package cirque.apptraduction.dto;

import java.sql.Date;
import java.sql.Time;

public class ConversationDto {

	private Date date;
	private Time time;
	private Boolean withGoogle;
	private int id;
	
	public ConversationDto() {
	}
	
	public ConversationDto(Date date, Time time, Boolean withGoogle, int id) {
		this.date = date;
		this.time = time;
		this.withGoogle = withGoogle;
		this.id = id;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public Time getTime() {
		return this.time;
	}
	
	public Boolean getWithGoogle() {
		return this.withGoogle;
	}
	
	public int getId() {
		return this.id;
	}
}
