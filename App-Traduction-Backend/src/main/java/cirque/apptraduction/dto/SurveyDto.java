package cirque.apptraduction.dto;

public class SurveyDto {

	private PersonDto person;
	private int helpsWork;
	private int replacesService;
	private int rating;
	
	public SurveyDto() {
	}
	
	public SurveyDto(int helpsWork, int replacesService, int rating, PersonDto person) {
		this.person = person;
		this.helpsWork = helpsWork;
		this.replacesService = replacesService;
		this.rating = rating;
	}
	
	public int getHelpsWork() {
		return this.helpsWork;
	}
	
	public int getReplacesService() {
		return this.replacesService;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public PersonDto getPerson() {
		return this.person;
	}
}
