

package myGame;


import java.util.ArrayList;
import myGame.components.AABBComponent;




public class AABBCollision
{
    private static ArrayList<AABBComponent> aabbList = new ArrayList<>();
    
    public static void addAABBComponent(AABBComponent aabb){
		aabbList.add(aabb);
	
    }
    
    public static void update(){
	for(int i = 0; i< aabbList.size(); i++){
	//	System.out.println(aabbList.get(i).getParent().getTag());
	    for(int j = 0; j < aabbList.size(); j++){
		AABBComponent c0 = aabbList.get(i);
		AABBComponent c1 = aabbList.get(j);
		
		if(Math.abs(c0.getCenterX() - c1.getCenterX()) <= c0.getHalfWidth() + c1.getHalfWidth()){ //x-collision
		    if(Math.abs(c0.getCenterY() - c1.getCenterY()) < c0.getHalfHeight() + c1.getHalfHeight()){ // y-collision
			//c0.getParent().collision(c1.getParent());
			c1.getParent().collision(c0.getParent());
		    }
		}
	    }
	}
	
	aabbList.clear();
    }
}
