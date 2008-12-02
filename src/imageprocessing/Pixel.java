package imageprocessing;


public class Pixel {

	/**
	 * @param args
	 */
	public int x;
	public int y;
	public int i;
	public int j;
	public int value;
	public int previous;
	public int in_front;
	public short [][] pixels;
	public boolean lookingWest=false, lookingNorth= false, lookingEast = false,lookingSouth = false;
	public Pixel(int x, int y, int value, int previous, int in_front){
		this.x = x;
		this.y = y;
		this.value = value;
		this.previous = previous;
		this.in_front = in_front;
	}
	public Pixel(int i, int j, int value){
		this.x = j;
		this.y = i;
		this.i = i;
		this.j = j;
		this.value = value;
		
	}
	public Pixel(int i, int j, int value, short[][] pixels){
		this.x = j;
		this.y = i;
		this.i = i;
		this.j = j;
		this.value = value;
		this.pixels = pixels;
		
	}
	public Pixel(int i, int j, short[][] pixels,int orientation){
		this.x = j;
		this.y = i;
		this.i = i;
		this.j = j;
		this.pixels = pixels;
		switch (orientation) {
		case 0:
			lookingEast= true;
		break;
		case 1:
			lookingNorth = true;
		break;
		case 2:
			lookingWest = true;
		break;
		case 3:
			lookingSouth = true;
		break;

		default:
		break;
		}
		
	}
	public Pixel(int i, int j){
		this.i = i;
		this.j = j;
		this.x = i;
		this.y = j;
		
	}
	public Pixel(int i2, int j2, short s, int k) {
		// TODO Auto-generated constructor stub
	}
	public boolean samePositionAs(Pixel p_pos) {
		if(this.i==p_pos.i&&this.j==p_pos.j)return true;
		else return false;
	}
	public void backup() {
		
		if(lookingEast)this.j--;
		if(lookingSouth)this.i--;
		
		
	}
	public Pixel position() {
		
		return new Pixel(i,j);
	}
	public int pixel_in_front() {
		if(lookingEast)return pixels[i][j+1];
		else if(lookingSouth)return pixels[i+1][j];
		else if(lookingWest)return pixels[i][j-1];
		else if(lookingNorth)return pixels[i-1][j];
		else return -1;
	}
	public void turnLeft() {
		if(lookingEast)	{
			//this.i--;
			lookingEast = false;
			lookingNorth = true;
		}
		else if(lookingSouth) {
			//this.j++;
			lookingSouth =false;
			lookingEast = true;
		}
		else if(lookingWest){
			lookingWest = false;
			lookingSouth = true;
		}
		else if(lookingNorth){
			lookingNorth = false;
			lookingWest = true;
		}
			
	}
	public int value() {
		// TODO Auto-generated method stub
		return pixels[i][j];
	}
	public void step() {
		if(lookingEast)this.j++;
		else if(lookingSouth)this.i++;
		else if(lookingWest)this.j--;
		else if(lookingNorth)this.i--;
		// TODO Auto-generated method stub
		
	}
	public void turnRigth() {
		if(lookingEast){
			//this.i++;
			lookingEast = false;
			lookingSouth = true;
		}
		else if(lookingSouth){
			//this.i++;
			lookingSouth = false;
			lookingWest = true;
		}
		else if(lookingWest){
			lookingWest = false;
			lookingNorth = true;
		}
		else if(lookingNorth){
			lookingNorth = false;
			lookingEast = true;
		}
		
	}
	public boolean greaterThan(Pixel farMost) {
		
		return (this.i>farMost.i||this.j>farMost.j);
	}
	
	public boolean isZero() {
		return (this.i==0&&this.j==0);
	}
	public void setGreater(Pixel p) {
		if(p.i>this.i)this.i = p.i;
		if(p.j>this.j)this.j = p.j;
		
	}
	public boolean lessThan(Pixel startPixel) {
		// TODO Auto-generated method stub
		return (this.i<startPixel.i||this.j<startPixel.j);
	}
	public void setLower(Pixel p) {
		if(p.i<this.i)this.i = p.i;
		if(p.j<this.j)this.j = p.j;
	}
}
