package com.avinash.jobengine.repository;

import com.avinash.jobengine.model.job.Job;
import com.avinash.jobengine.model.job.JobStatus;
import com.avinash.jobengine.model.job.JobType;

import java.sql.*;
import java.util.Optional;

public class JobRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/jobengine?TimeZone=Asia/Kolkata";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void insert(Job job) {
        String sql = """
                INSERT INTO jobs(
                    id, type, priority, payload, status, created_at, updated_at, retry_count, max_retries
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) 
                ON CONFLICT (id) DO NOTHING
        """;
        try(Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, job.getId());
            ps.setString(2, job.getType().name());
            ps.setString(3, job.getPriority());
            ps.setString(4, job.getPayload());
            ps.setString(5, job.getStatus().name());
            ps.setTimestamp(6, Timestamp.valueOf(job.getCreatedAt()));
            ps.setTimestamp(7, Timestamp.valueOf(job.getUpdatedAt()));
            ps.setInt(8, job.getRetryCount());
            ps.setInt(9, job.getMaxRetries());

            ps.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("Failed to insert job: " + job.getId(), e);
        }
    }

    public Optional<Job> findById(String id) {
        String sql = "SELECT * FROM jobs WHERE id = ?";

        try(Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return Optional.of(mapRow(rs));
            }

            return Optional.empty();
        }catch (SQLException e) {
            throw new RuntimeException("Failed to find job: " + id, e);
        }
    }

    public void updateStatus(Job job) {
        String sql = """
                UPDATE jobs SET 
                    status = ?,
                    updated_at = ?,
                    processed_by = ?,
                    retry_count = ?,
                    error_message = ?,
                    execution_time_ms = ?
                WHERE id = ? 
                """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, job.getStatus().name());
            ps.setTimestamp(2, Timestamp.valueOf(job.getUpdatedAt()));
            ps.setString(3, job.getProcessedBy());
            ps.setInt(4, job.getRetryCount());
            ps.setString(5, job.getErrorMessage());
            ps.setLong(6, job.getExecutionTimeMs());
            ps.setString(7, job.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update job: " + job.getId(), e);
        }
    }

    public int countByStatus(JobStatus status) {
        String sql = "SELECT COUNT(*) FROM jobs WHERE status = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
              return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count jobs by status", e);
        }
    }

    public void insertAudit(String jobId, JobStatus fromStatus, JobStatus toStatus, String changedBy, String notes) {
        String sql = """
               INSERT INTO job_audit (
               job_id, from_status, to_status, changed_by, notes
               ) VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            ps.setString(2, fromStatus != null ? fromStatus.name() : null);
            ps.setString(3, toStatus.name());
            ps.setString(4, changedBy);
            ps.setString(5, notes);

            ps.executeUpdate();

        } catch (SQLException e) {
            // Audit failure should not fail the main flow
            System.err.println("Warning: Failed to insert audit for job "
                    + jobId + ": " + e.getMessage());
        }
    }
    private Job mapRow(ResultSet rs) throws SQLException {
        Job job = new Job();
        job.setId(rs.getString("id"));
        job.setType(JobType.valueOf(rs.getString("type")));
        job.setPriority(rs.getString("priority"));
        job.setPayload(rs.getString("payload"));
        job.setStatus(JobStatus.valueOf(rs.getString("status")));
        job.setRetryCount(rs.getInt("retry_count"));
        job.setMaxRetries(rs.getInt("max_retries"));
        job.setProcessedBy(rs.getString("processed_by"));
        job.setErrorMessage(rs.getString("error_message"));
        job.setExecutionTimeMs(rs.getLong("execution_time_ms"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        if (createdAt != null) job.setCreatedAt(createdAt.toLocalDateTime());
        if (updatedAt != null) job.setUpdatedAt(updatedAt.toLocalDateTime());

        return job;
    }
}
