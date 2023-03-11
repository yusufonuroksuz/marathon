
import java.util.HashMap;

public class node implements Comparable<node>{
	
	String name;
	HashMap<node, Integer> adjacents = new HashMap<>(200);
	int distance;
	int is_fixed = 0;
	int is_fixed_nt = -1;
	public node(String name) {
		this.name = name;
	}
	public void assign_adj(node node, Integer lenght) {
		this.adjacents.put(node, lenght);
		node.adjacents.put(this, lenght);
	}
	@Override
	public int compareTo(node o) {
		// TODO Auto-generated method stub
		return this.distance - o.distance;
	}
	
	
	
	
}
