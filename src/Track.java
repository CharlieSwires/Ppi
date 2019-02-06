
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "id")
@XmlAccessorType (XmlAccessType.FIELD)
public class Track {

	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "secondaryCode")
	private String secondaryCode;
	@XmlElement(name = "emetres")
	private Long emetres;
	@XmlElement(name = "nmetres")
	private Long nmetres;
	@XmlElement(name = "hmetres")
	private Long hmetres;
	@XmlElement(name = "demetrespersecond")
	private Long demetrespersecond;
	@XmlElement(name = "dnmetrespersecond")
	private Long dnmetrespersecond;
	@XmlElement(name = "dhmetrespersecond")
	private Long dhmetrespersecond;
	@XmlElement(name = "signalstrengthdDm")
	private Integer signalstrengthdDm;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSecondaryCode() {
		return secondaryCode;
	}
	public void setSecondaryCode(String secondaryCode) {
		this.secondaryCode = secondaryCode;
	}
	public Long getEmetres() {
		return emetres;
	}
	public void setEmetres(Long emetres) {
		this.emetres = emetres;
	}
	public Long getNmetres() {
		return nmetres;
	}
	public void setNmetres(Long nmetres) {
		this.nmetres = nmetres;
	}
	public Long getHmetres() {
		return hmetres;
	}
	public void setHmetres(Long hmetres) {
		this.hmetres = hmetres;
	}
	public Long getDemetrespersecond() {
		return demetrespersecond;
	}
	public void setDemetrespersecond(Long demetrespersecond) {
		this.demetrespersecond = demetrespersecond;
	}
	public Long getDnmetrespersecond() {
		return dnmetrespersecond;
	}
	public void setDnmetrespersecond(Long dnmetrespersecond) {
		this.dnmetrespersecond = dnmetrespersecond;
	}
	public Long getDhmetrespersecond() {
		return dhmetrespersecond;
	}
	public void setDhmetrespersecond(Long dhmetrespersecond) {
		this.dhmetrespersecond = dhmetrespersecond;
	}
	public Integer getSignalstrengthdDm() {
		return signalstrengthdDm;
	}
	public void setSignalstrengthdDm(Integer signalstrengthdDm) {
		this.signalstrengthdDm = signalstrengthdDm;
	}
	@Override
	public String toString() {
		return "Track [id=" + id + ", secondaryCode=" + secondaryCode + ", emetres=" + emetres + ", nmetres=" + nmetres
				+ ", hmetres=" + hmetres + ", demetrespersecond=" + demetrespersecond + ", dnmetrespersecond="
				+ dnmetrespersecond + ", dhmetrespersecond=" + dhmetrespersecond + ", signalstrengthdDm="
				+ signalstrengthdDm + "]";
	}

}
