package projects.dao;

import provided.util.DaoBase;
import projects.entity.Project;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import projects.Exception.DbException;


@SuppressWarnings("unused")
public class ProjectDao extends DaoBase{
    // Constants for table names
    private static final String CATEGORY_TABLE = "category";
    private static final String MATERIAL_TABLE = "material";
    private static final String PROJECT_TABLE = "project";
    private static final String PROJECT_CATEGORY_TABLE = "project_category";
    private static final String STEP_TABLE = "step";

    

    public Project insertProject(Project project) {
        // @formatter:off
        String sql = "INSERT INTO " + PROJECT_TABLE +
                " (project_name, estimated_hours, actual_hours, difficulty, notes) " +
                "VALUES (?, ?, ?, ?, ?)";
        // @formatter:on
        try (Connection conn = DbConnection.getConnection()) {
            // Start a transaction
            startTransaction(conn);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set parameters in the PreparedStatement
                setParameter(stmt, 1, project.getProjectName(), String.class);
                setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
                setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
                setParameter(stmt, 4, project.getDifficulty(), Integer.class);
                setParameter(stmt, 5, project.getNotes(), String.class);

              
                stmt.executeUpdate();

                // Obtain the project ID
                Integer projectId = getLastInsertId(conn, PROJECT_TABLE);

                // Commit the transaction
                commitTransaction(conn);

                // Set the projectId on the Project object and return it
                project.setProjectId(projectId);
                return project;
            } catch (Exception e) {
                // Roll back the transaction on any exception
                rollbackTransaction(conn);
                throw new DbException(e);
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

}
