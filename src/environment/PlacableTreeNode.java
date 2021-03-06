package environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.Vector;

public class PlacableTreeNode implements IPlacableManager {
	
	/**
	 * set of sub nodes
	 */
	private PlacableTreeNode[] subNodes;
	
	/**
	 * list of items on this node
	 */
	private Set<IPlacable> items;
	
	/**
	 * instance variables
	 */
	private float minX;
	private float minY;
	private float halfX;
	private float halfY;
	private int maxDepth;
	
	/**
	 * constructor
	 * @param minX
	 * @param minY
	 * @param maxX
	 * @param maxY
	 * @param maxDepth
	 */
	public PlacableTreeNode (float minX, float minY, float maxX,float maxY,int maxDepth){
		this.minX = minX;
		this.minY = minY;
		if (maxDepth > 0){
			this.halfX = maxX/2;
			this.halfY = maxY/2;
		}else{
			this.items = new HashSet<IPlacable>();
		}
		this.maxDepth = maxDepth;
	}


	@Override
	public void add(IPlacable placable){
		if (this.maxDepth > 0){
			if (subNodes == null){
				subNodes = new PlacableTreeNode[4];
			}
			int index = 0;
			float leftX = this.minX;
			float rightX = this.minX+this.halfX;
			float topY = this.minY;
			float bottomY = this.minY+this.halfY;
			if (placable.getXPos() > (this.minX+this.halfX)){
				index+=1;
				leftX = rightX;
				//rightX += this.halfX;
			}
			if(placable.getYPos() > (this.minY+this.halfY)){
				index+=2;
				topY = bottomY;
				//bottomY+= this.halfY;
			}
			if (this.subNodes[index]== null){
				this.subNodes[index] = new PlacableTreeNode(leftX,topY,this.halfX,this.halfY,this.maxDepth-1);
			}
			this.subNodes[index].add(placable);
		}else{
			this.items.add(placable);
		}
	}
	
	@Override
	public boolean move(IMovable movable, float newX, float newY) {
		// TODO change this method completely as it doesn't work at all :( 
		float xPos = movable.getXPos();
		float yPos = movable.getYPos();
		if (this.maxDepth > 0){
			int index =0;
			if (xPos > (this.minX+this.halfX)){
				index+=1;
			}
			if(yPos > (this.minY+this.halfY)){
				index+=2;
			}
			if (this.subNodes[index]!=null){
				boolean removed = this.subNodes[index].move(movable,newX,newY);
				if (removed){
					if (
							(newX > this.minX && newX < (this.minX+2*this.halfX))
							&& (newY > this.minY && newY < (this.minY + 2*this.halfY))
					)
					{
						movable.updatePosition(new Vector(new float[]{newX,newY}));
						this.add(movable);
						return false;
					}else{
						return true;
					}
				}
				return removed;
			}
			return false;
		}else{
			if (items.contains(movable)){
				items.remove(movable);
				return true;
			}
			return false;
		}
	}

	@Override
	public void remove(IPlacable placable) throws Exception{
		if (this.maxDepth > 0){
			int index =0;
			if (placable.getXPos() > (this.minX+this.halfX)){
				index+=1;
				
			}
			if(placable.getYPos() > (this.minY+this.halfY)){
				index+=2;
			}
			if (this.subNodes[index]!=null){
				this.subNodes[index].remove(placable);
			}
		}else{
			this.items.remove(placable);
		}
	}
	
	/**
	 * finds a way point by it's x and y coordinates
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public List<IPlacable> find(float xPos,float yPos){
		if (this.maxDepth > 0){
			int index =0;
			if (xPos > (this.minX+this.halfX)){
				index+=1;
			}
			if(yPos > (this.minY+this.halfY)){
				index+=2;
			}
			if (this.subNodes[index]!=null){
				return this.subNodes[index].find(xPos,yPos);
			}
		}else{
			return new ArrayList<IPlacable> (this.items);
		}
		return null;
	}
	
	/**
	 * finds way points in the area defined by minX, minY, maxX, maxY
	 * @param minX
	 * @param minY
	 * @param maxX
	 * @param maxY
	 * @return
	 */
	public List<IPlacable> findInArea(float minX,float minY,float maxX,float maxY){
		if (this.maxDepth > 0){
			List<IPlacable> output = new ArrayList<IPlacable>();
			List<IPlacable> result;
			if (minX < (this.minX+this.halfX)){
				if (minY < (this.minY+this.halfY)){
				//System.out.println("looking in 0 "+this.minX+"->"+(this.minX+this.halfX)+";"+this.minY+"->"+(this.minY+this.halfY)+" at depth "+this.maxDepth);
					if (this.subNodes[0] != null){
						result = this.subNodes[0].findInArea(minX, minY, maxX, maxY);
						if (result != null){
							if (result.size()>0){
							//System.out.println("found something!"+result.size());
								output.addAll(result);
							}
						}
					}
					if (maxX > (this.minX+this.halfX)){
					//System.out.println("looking in 1 "+(this.minX+this.halfX)+"->"+(this.minX+2*this.halfX)+";"+this.minY+"->"+(this.minY+this.halfY)+" at depth "+this.maxDepth);
						if (this.subNodes[1] != null){
							result = this.subNodes[1].findInArea(minX, minY, maxX, maxY);
							if (result != null){
								if (result.size()>0){
								//System.out.println("found something!"+result.size());
									output.addAll(result);
								}
							}
						}
						if (maxY > (this.minY+this.halfY)){
						//System.out.println("looking in 3 "+(this.minX+this.halfX)+"->"+(this.minX+2*this.halfX)+";"+(this.minY+this.halfY)+"->"+(this.minY+2*this.halfY)+" at depth "+this.maxDepth);
							if (this.subNodes[3] != null){
								result = this.subNodes[3].findInArea(minX, minY, maxX, maxY);
								if (result != null){
									if (result.size()>0){
									//System.out.println("found something!"+result.size());
										output.addAll(result);
									}
								}
							}
						}
					}
					if (maxY > (this.minY+this.halfY)){
					//System.out.println("looking in 2 "+(this.minX)+"->"+(this.minX+this.halfX)+";"+(this.minY+this.halfY)+"->"+(this.minY+2*this.halfY)+" at depth "+this.maxDepth);
						if (this.subNodes[2] != null){
							result = this.subNodes[2].findInArea(minX, minY, maxX, maxY);
							if (result != null){
								if (result.size()>0){
								//System.out.println("found something!"+result.size());
									output.addAll(result);
								}
							}
						}
					}
				}else if(minY < (this.minY+2*this.halfY)){
				//System.out.println("looking in 2 "+this.minX+"->"+(this.minX+this.halfX)+";"+(this.minY+this.halfY)+"->"+(this.minY+2*this.halfY)+" at depth "+this.maxDepth);
					if (this.subNodes[2] != null){
						result = this.subNodes[2].findInArea(minX, minY, maxX, maxY);
						if (result != null){
							if (result.size()>0){
							//System.out.println("found something!"+result.size());
								output.addAll(result);
							}
						}
					}
					if (maxX > (this.minX+this.halfX)){
					//System.out.println("looking in 3 "+(this.minX+this.halfX)+"->"+(this.minX+2*this.halfX)+";"+(this.minY+this.halfY)+"->"+(this.minY+2*this.halfY)+" at depth "+this.maxDepth);
						if (this.subNodes[3] != null){
							result = this.subNodes[3].findInArea(minX, minY, maxX, maxY);
							if (result != null){
								if (result.size()>0){
								//System.out.println("found something!"+result.size());
									output.addAll(result);
								}
							}
						}
					}
				}
			}else if(minX <(this.minX+2*this.halfX)){	
				if (minY < (this.minY+this.halfY)){
				//System.out.println("looking in 1 "+(this.minX+this.halfX)+"->"+(this.minX+2*this.halfX)+";"+(this.minY)+"->"+(this.minY+this.halfY)+" at depth "+this.maxDepth);
					if (this.subNodes[1] != null){
						result = this.subNodes[1].findInArea(minX, minY, maxX, maxY);
						if (result != null){
							if (result.size()>0){
							//System.out.println("found something!"+result.size());
								output.addAll(result);
							}
						}
					}
					if (maxY > (this.minY+this.halfY)){
					//System.out.println("looking in 3 "+(this.minX+this.halfX)+"->"+(this.minX+2*this.halfX)+";"+(this.minY+this.halfY)+"->"+(this.minY+2*this.halfY)+" at depth "+this.maxDepth);
						if (this.subNodes[3] != null){
							result = this.subNodes[3].findInArea(minX, minY, maxX, maxY);
							if (result != null){
								if (result.size()>0){
								//System.out.println("found something!"+result.size());
									output.addAll(result);
								}
							}
						}
					}
				}else if(minY < (this.minY+2*this.halfY)){
				//System.out.println("looking in 3 "+(this.minX+this.halfX)+"->"+(this.minX+2*this.halfX)+";"+(this.minY+this.halfY)+"->"+(this.minY+2*this.halfY)+" at depth "+this.maxDepth);
					if (this.subNodes[3] != null){
						result = this.subNodes[3].findInArea(minX, minY, maxX, maxY);
						if (result != null){
							if (result.size()>0){
							//System.out.println("found something!"+result.size());
								output.addAll(result);
							}
						}
					}
				}
			}
			return output;
		}else{
			if (this.items != null){
				return new ArrayList<IPlacable> (this.items);
			}else{
				return null;
			}
		}
	}
	
	@Override
	public List<IPlacable> toList (){
		if (this.maxDepth == 0){
			return new ArrayList<IPlacable> (this.items);
		}else{
			List<IPlacable> output = null;
			if (this.subNodes != null){
				for (IPlacableManager child:this.subNodes){
					if (child!=null){
						if (output != null){
							output.addAll(child.toList());
						}else{
							output = child.toList();
						}
					}
				}
			}
			return output;
		}
	}
}