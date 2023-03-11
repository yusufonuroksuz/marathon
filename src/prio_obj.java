
public class prio_obj implements Comparable<prio_obj>{
	
	node node;
	int distance;

	public prio_obj(node node, int distance) {
		this.distance = distance;
		this.node = node;
	}

	@Override
	public int compareTo(prio_obj o) {
		return this.distance - o.distance;
	}

	
	
	
	
}
