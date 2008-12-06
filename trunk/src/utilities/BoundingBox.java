package utilities;
public class BoundingBox {
	public int minX,maxX,minY,maxY;
	public BoundingBox(int minX, int maxX, int minY, int maxY) {
		this.minX = minX;
		this.maxX= maxX;
		this.minY= minY;
		this.maxY=maxY;
		
	}
	public int getWidth() {
		return maxX - minX;
	}

	public int getHeigth() {
		return maxY - minY;
	}

}