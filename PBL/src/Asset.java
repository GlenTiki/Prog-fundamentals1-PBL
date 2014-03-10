public class Asset {

	private boolean oilWell;
	private boolean refinery;
	private boolean drillingRig;
	private boolean gasStation;

	public Asset() {
		oilWell = false;
		refinery = false;
		drillingRig = false;
		gasStation = false;
	}

	public void setAssetTo(String assetType) {
		if (assetType.equals("refinery")) {
			refinery = true;
		} else if (assetType.equals("oilWell")) {
			oilWell = true;
		} else if (assetType.equals("gasStation")) {
			gasStation = true;
		} else if (assetType.equals("drillingRig")) {
			drillingRig = true;
		}
	}

	public String checkForAsset() {
		if (drillingRig) {
			return "D";
		} else if (oilWell) {
			return "O";
		} else if (refinery) {
			return "R";
		} else if (gasStation) {
			return "G";
		} else {
			return "-";
		}
	}
	
	public void deleteAsset(){
		oilWell = false;
		refinery = false;
		drillingRig = false;
		gasStation = false;
	}

}
