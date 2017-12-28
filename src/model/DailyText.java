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
	private String überschrift;
	private String text;
	private String tägliches;
	
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
	public String getÜberschrift() {
		return überschrift;
	}
	public void setÜberschrift(String überschrift) {
		this.überschrift = überschrift;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTägliches() {
		return tägliches;
	}
	public void setTägliches(String tägliches) {
		this.tägliches = tägliches;
	}
	
	
}
