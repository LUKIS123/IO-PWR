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
            System.out.print(j.getJobId() + " ");
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
//        System.out.print(j.getJobId() + " ");
//        System.out.print(j.getDriverId() + " ");
//        System.out.print(j.getClientId() + " ");
//        System.out.print(j.getCargoType() + " ");
//        System.out.print(j.getStatus() + " ");
//        System.out.print(j.getDistance() + " ");
//        System.out.print(j.getWeight() + " ");
//        System.out.println();
//        System.out.println();

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
                model.getJobId(),
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

    public List<Job> getByStatus(JobStatus status) throws SQLException {
        List<Job> jobList = new ArrayList<>();

        String query = "SELECT * from jobs where jobstatus = ";
        query = query + status.toString() + ";";

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
        return jobList;
    }

    // todo sprawdzic
    public List<JobDriverAssignmentDto> getByStatusWithDriverSuggestion(JobStatus status) throws SQLException {
        List<Job> byStatus = getByStatus(JobStatus.PAID);

        List<Driver> driverList = new ArrayList<>();
        String query = "SELECT nick from Drivers\n" +
                "WHERE duringrest = FALSE\n" +
                "AND duringexecutionoforder = FALSE;";

        ResultSet Data = DatabaseConnectionSettings.getDataFromDatabse(query);
        while (Data.next()) {
            String nick = Data.getString("nick");
            Driver d = new Driver(nick);
            driverList.add(d);
        }

        if (driverList.isEmpty()) {
            String query2 = "SELECT nick, duringrest, duringexecutionoforder from Drivers;";
            ResultSet Data2 = DatabaseConnectionSettings.getDataFromDatabse(query2);

            while (Data2.next()) {
                String nick = Data2.getString("nick");
                Boolean duringrest = Data2.getBoolean("duringrest");
                Boolean duringexecutionoforder = Data2.getBoolean("duringexecutionoforder");

                Driver d = new Driver(nick, duringrest, duringexecutionoforder);
                driverList.add(d);
            }
        }

        List<JobDriverAssignmentDto> assignmentDtos = new ArrayList<>();
        int size = driverList.size();
        int i = 0;
        for (Job job : byStatus) {
            if (i >= size) {
                i = 0;
            }
            job.setDriverId(driverList.get(i).getId());
            assignmentDtos.add(new JobDriverAssignmentDto(driverList.get(i), job));
            ++i;
        }
        return assignmentDtos;
    }

    public void updateJobDriverAssignment(int driverId, int jobId) throws SQLException {
        Job byId = getById(jobId);
        byId.setDriverId(driverId);
        update(jobId, byId);
    }

    public Job getAssignedJob(int driverId) {
        return null;
    }
}
