package mitro.controller;

public class InfoDeployment {

	private static InfoDeployment instance;
	
	private InfoDeployment() {
		
	}
	
	public static InfoDeployment getInstance() {
		if(instance == null)
			instance = new InfoDeployment();
		return instance;
	}
	
	public int getMajor() {
		return 1;
	}
	
	public int getMinor() {
		return 0;
	}
	
	public int getRevision() {
		return 0;
	}
	
	public String getName() {
		return "Mitro";
	}
	
	public String toString() {
		return getName() + " - v" + getMajor() + "." + getMinor() + "." + getRevision();
	}
	
}
