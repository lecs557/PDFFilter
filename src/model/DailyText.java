package model;
/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class DailyText {

	private String datum;
	private String vers;
	private String stelle;
	private String �berschrift;
	private String text;
	private String t�gliches;
	
	public DailyText() {	}
	
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getVers() {
		return vers;
	}
	public void setVers(String vers) {
		this.vers = vers;
	}
	public String getStelle() {
		return stelle;
	}
	public void setStelle(String stelle) {
		this.stelle = stelle;
	}
	public String get�berschrift() {
		return �berschrift;
	}
	public void set�berschrift(String �berschrift) {
		this.�berschrift = �berschrift;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getT�gliches() {
		return t�gliches;
	}
	public void setT�gliches(String t�gliches) {
		this.t�gliches = t�gliches;
	}
	
	
}
