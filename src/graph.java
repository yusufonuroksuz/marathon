import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class graph {
	HashMap<String, node> nodemap = new HashMap<>();
	ArrayList<node> nodes = new ArrayList<>();
	int node_count = 0;

	public graph() {
	}
	public void add_node(node node) {
		this.nodemap.put(node.name, node);
		nodes.add(node);
		node_count++;
	}
	
	
	public int shortest_path(node start, node end) {
		PriorityQueue<prio_obj> queue = new PriorityQueue<>();
		prio_obj startobj = new prio_obj(start, 0);
		queue.add(startobj);
		HashSet<node> fixed = new HashSet<>();
		while (queue.size() > 0) {
			prio_obj v = queue.poll();
			if (v.node.name == end.name) return v.distance;
			if (fixed.contains(v.node)) continue;
			fixed.add(v.node);
			for (Entry<node, Integer> adj : v.node.adjacents.entrySet()) {
				int newdistance = v.distance + adj.getValue();
				prio_obj to_queue = new prio_obj(adj.getKey(), newdistance);
				queue.add(to_queue);
			}
		}
		return -1;
	}
	
	public int prims(node root) {
		PriorityQueue<prio_obj> queue = new PriorityQueue<>();
		
		int total_weight = 0;
		int count = 0;
		
		for (node node : this.nodes) {
			node.is_fixed = 0;
		}
		prio_obj rootobj = new prio_obj(root, 0);
		queue.add(rootobj);
		
		
		while (queue.size() > 0) {
			prio_obj v = queue.poll();
			if (v.node.is_fixed == 1) continue;
			v.node.is_fixed = 1;
			count++;
			total_weight += v.distance;
			for (Entry<node, Integer> adj : v.node.adjacents.entrySet()) {
				if (adj.getKey().is_fixed == 1) continue;
				prio_obj adjobj = new prio_obj(adj.getKey(), adj.getValue());
				queue.add(adjobj);
			}
		}
		if (count == this.node_count) return total_weight;
		return -1;
		
	}
	
	
	
	
	
	
	

}
