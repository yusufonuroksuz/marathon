import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class project4 {
	
	
	

	public static void main(String[] args) throws IOException {
		
		
		FileReader inputFile = new FileReader("stress1.txt");
		BufferedReader input = new BufferedReader(inputFile);
		File outputFile = new File("myout.txt");
		FileWriter output = new FileWriter(outputFile);
		
		graph istanbul = new graph();
		graph istanbul_flags = new graph();
		
		int node_no = Integer.parseInt(input.readLine().split(" ")[0]);
		int flag_no = Integer.parseInt(input.readLine().split(" ")[0]);
		String[] path = input.readLine().split(" ");
		String start = path[0];
		String end = path[1];
		
		String[] flag_line = input.readLine().split(" ");
		ArrayList<String> flags = new ArrayList<>();
		for (String name : flag_line) flags.add(name);
		
		HashMap<String, node> nodes = new HashMap<>(node_no);
		
		for (int i = 0; i < node_no; i++) {
			String[] line = input.readLine().split(" ");
			if (nodes.containsKey(line[0])) {
				node main_node = nodes.get(line[0]);
				for (int j = 1; j < line.length; j = j+2) {
					String name = line[j];
					int lenght = Integer.parseInt(line[j+1]);
					if (nodes.containsKey(name)) {
						node adj_node = nodes.get(name);
						main_node.assign_adj(adj_node, lenght);
					}
					else {
						node adj_node = new node(name);
						istanbul.add_node(adj_node);
						nodes.put(name, adj_node);
						main_node.assign_adj(adj_node, lenght);
					}
				}
			}
			else {
				node main_node = new node(line[0]);
				istanbul.add_node(main_node);
				nodes.put(line[0], main_node);
				for (int j = 1; j < line.length; j = j+2) {
					String name = line[j];
					int lenght = Integer.parseInt(line[j+1]);
					if (nodes.containsKey(name)) {
						node adj_node = nodes.get(name);
						main_node.assign_adj(adj_node, lenght);
					}
					else {
						node adj_node = new node(name);
						istanbul.add_node(adj_node);
						nodes.put(name, adj_node);
						main_node.assign_adj(adj_node, lenght);
					}
				}
			}	
		}
		
		
		int race_lenght = istanbul.shortest_path(nodes.get(start), nodes.get(end));
		output.write(race_lenght + "\n");
		
		
		
		for (String flag_name : flags) {
			node flag = new node(flag_name);
			istanbul_flags.add_node(flag);
		}
		
		
		for (node flag : istanbul_flags.nodes) {
			int count = 0;
			node in_ist = nodes.get(flag.name);
			HashSet<node> fixed = new HashSet<>();
			PriorityQueue<prio_obj> queue = new PriorityQueue<>();
			prio_obj rootobj = new prio_obj(in_ist, 0);
			queue.add(rootobj);
			while (queue.size() > 0) {
				if (count == flag_no) break; 
				prio_obj v = queue.poll();
				if (fixed.contains(v.node)) continue;
				if (istanbul_flags.nodemap.containsKey(v.node.name)) {
					flag.assign_adj(istanbul_flags.nodemap.get(v.node.name), v.distance);
					count++;
				}
				fixed.add(v.node);
				for (Entry<node, Integer> adj : v.node.adjacents.entrySet()) {
					int newdistance = v.distance + adj.getValue();
					prio_obj to_queue = new prio_obj(adj.getKey(), newdistance);
					queue.add(to_queue);
				}
			}
			
			
			
		}
		
		int mst_weight = istanbul_flags.prims(istanbul_flags.nodes.get(0));
		output.write(mst_weight);
		
		
		input.close();
		output.close();

	}

}
