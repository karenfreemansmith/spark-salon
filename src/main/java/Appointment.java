import org.sql2o.*;
import java.util.List;
import java.util.Date;

public class Appointment {
  private int id=0;
  private String time;
  private int clientid;
  private int procedureid;

  public Appointment (String time, int clientid, int procedureid) {
    this.time = time;
    this.clientid = clientid;
    this.procedureid = procedureid;

    try(Connection cn = DB.sql2o.open()) {
      String sql = "INSERT INTO appointments (time, clientid, procedureid) VALUES (:time, :clientid, :procedureid)";
      this.id = (int) cn.createQuery(sql, true)
        .addParameter("time", this.time)
        .addParameter("clientid", this.clientid)
        .addParameter("procedureid", this.procedureid)
        .executeUpdate()
        .getKey();
    }
  }

  public int getId() {
    return id;
  }

  public String getTime() {
    return time;
  }

  public int getClientId() {
    return clientid;
  }

  public int getProcedureId() {
    return procedureid;
  }

  public static Appointment find(int id) {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "SELECT * FROM appointments WHERE id=:id";
      Appointment appointment = cn.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Appointment.class);
      return appointment;
    }
  }

}
