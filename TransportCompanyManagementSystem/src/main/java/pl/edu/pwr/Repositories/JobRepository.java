package pl.edu.pwr.Repositories;

import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.utility.DatabaseConnectionSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobRepository implements RepositoryInterface<Job> {
    @Override
    public List<Job> getAll() throws SQLException {
        List<Job> jobList = new ArrayList<>();

        String query = "SELECT * from jobs;";
        ResultSet Data = DatabaseConnectionSettings.getDataFromDatabse(query);

        while (Data.next()) {
            int jobid = Data.getInt("jobid");
            int driverid = Data.getInt("driverid");
            int clientid = Data.getInt("clientid");
            String cargotype = Data.getString("cargotype");
            String jobstatus = Data.getString("jobstatus");
            int distance = Data.getInt("distance");
            int weight = Data.getInt("weight");
            Boolean ispaid = Data.getBoolean("ispaid");

            Job j = new Job(
                    jobid,
                    driverid,
                    clientid,
                    cargotype,
                    jobstatus,
                    distance,
                    weight,
                    ispaid);
            jobList.add(j);
        }

        //todo usuń to potem
        for (Job j : jobList) {
            System.out.print(j.getJob_Id() + " ");
            System.out.print(j.getDriverId() + " ");
            System.out.print(j.getClientId() + " ");
            System.out.print(j.getCargoType() + " ");
            System.out.print(j.getStatus() + " ");
            System.out.print(j.getDistance() + " ");
            System.out.print(j.getWeight() + " ");
            System.out.println();
            System.out.println();
        }
        return jobList;
    }

    @Override
    public Job getById(int id) throws SQLException {
        String query = "SELECT * from jobs where jobid = ";
        query = query + id + ";";

        ResultSet Data = DatabaseConnectionSettings.getDataFromDatabse(query);

        Data.next();
        int jobid = Data.getInt("jobid");
        int driverid = Data.getInt("driverid");
        int clientid = Data.getInt("clientid");
        String cargotype = Data.getString("cargotype");
        String jobstatus = Data.getString("jobstatus");
        int distance = Data.getInt("distance");
        int weight = Data.getInt("weight");
        Boolean ispaid = Data.getBoolean("ispaid");

        Job j = new Job(
                jobid,
                driverid,
                clientid,
                cargotype,
                jobstatus,
                distance,
                weight,
                ispaid);

        //todo usuń to potem
        System.out.print(j.getJob_Id() + " ");
        System.out.print(j.getDriverId() + " ");
        System.out.print(j.getClientId() + " ");
        System.out.print(j.getCargoType() + " ");
        System.out.print(j.getStatus() + " ");
        System.out.print(j.getDistance() + " ");
        System.out.print(j.getWeight() + " ");
        System.out.println();
        System.out.println();

        return j;
    }

    @Override
    public void insert(Job model) throws SQLException {
        String query = String.format("INSERT INTO Jobs" +
                        "( jobid," +
                        " driverid," +
                        " clientid," +
                        " cargotype," +
                        "jobstatus," +
                        "distance," +
                        "weight," +
                        "ispaid)" +
                        "VALUES" +
                        "(nextval('jobs_id_seq'),%d,%d,'%s','%s',%d,%d,%b);",
                model.getDriverId(),
                model.getClientId(),
                model.getCargoType().toString(),
                model.getStatus().toString(),
                model.getDistance(),
                model.getWeight(),
                model.isPaid());

        //System.out.println(query);
        DatabaseConnectionSettings.executeQuery(query);
    }

    @Override
    public void update(int id, Job model) throws SQLException {
        String query = String.format("UPDATE Jobs " +
                        "SET " +
                        "jobid = %d,       " +
                        "driverid = %d,    " +
                        "clientid = %d,    " +
                        "cargotype = '%s',   " +
                        "jobstatus = '%s',   " +
                        "distance = %d,    " +
                        "weight = %d,      " +
                        "ispaid = %b       " +
                        "WHERE jobid = %d;",
                model.getJob_Id(),
                model.getDriverId(),
                model.getClientId(),
                model.getCargoType().toString(),
                model.getStatus().toString(),
                model.getDistance(),
                model.getWeight(),
                model.isPaid(),
                id);
        DatabaseConnectionSettings.executeQuery(query);
    }


    @Override
    public void delete(int id) throws SQLException {
        String query = String.format("DELETE FROM jobs WHERE jobid = %d;", id);
        DatabaseConnectionSettings.executeQuery(query);
    }

    public List<Job> getByUserId(int userId) {
        return null;
    }

    public List<Job> getByStatus(JobStatus status) {
        return null;
    }

    public List<JobDriverAssignmentDto> getByStatusWithDriverSuggestion(JobStatus status) {
        return null;
        //todo query do bazy i info
    }

    public void updateJobDriverAssignment(int driverId) {
    }

    public Job getAssignedJob(int driverId) {
        return null;
    }
}
