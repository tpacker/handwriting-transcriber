package classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ViterbiTree {
	
	private ArrayList<ViterbiTree> children;
	private ViterbiTree parent;
	private String stateValue;
	private double runningLogProb;
	
	private static ViterbiTree bottom;
	private static double bottomMax;
	
	private RecordCell cell;
	
	public static void labelSequence(ProbabilityModel model, RecordRow row) {
		ViterbiTree root = new ViterbiTree(model, row, null, -1, null, 0, 0, 0); 
		root.setPredictions(model);
		
	}
	
	private ViterbiTree(ProbabilityModel model, RecordRow row, ViterbiTree parent, int stateIndex, String stateValue, double runningLogProb, double observationProb, double transitionProb) {
		if (parent == null)
			bottomMax = Double.NEGATIVE_INFINITY;
		this.parent = parent;
		this.stateValue = stateValue;
		this.children = null;
		if (stateIndex >= 0) {
			cell = row.getCells().get(model.stateCellOrder[stateIndex]);
			cell.setPredictedObservationProbability(observationProb);
			cell.setPredictedTransitionProbability(transitionProb);
		}
		this.runningLogProb = runningLogProb;
		if (stateIndex < model.columnCount-1) {
			stateIndex++;
			
			Map<Integer, HashMap<String, Integer>> stateValues = model.getStateValues();
			Set<String> values = stateValues.get(stateIndex).keySet();
			this.children = new ArrayList<ViterbiTree>();
			for (String value : values) {		
				observationProb = model.observationProbability(stateIndex, value, row.getCells().get(model.stateCellOrder[stateIndex]).getFeatures());
				transitionProb = model.transitionProbability(stateIndex, this.stateValue, value);
				//System.out.println("vcalc state:" + stateIndex + ":"+stateValue+"->"+value+"  observ:" + observationProb + "  trans:" + transitionProb);
				double childLogProb = runningLogProb + Math.log(observationProb) + Math.log(transitionProb);
				children.add(new ViterbiTree(model, row, this, stateIndex, value, childLogProb, observationProb, transitionProb));
			}
		} else if (this.runningLogProb > bottomMax) {
			bottomMax = this.runningLogProb;
			bottom = this;
		}
	}
	
	public void setPredictions(ProbabilityModel model) 
	{
		ViterbiTree current = bottom;
		while (current.parent != null) {
			current.cell.setPredictedTranscription(current.stateValue);
			current = current.parent;
		}
	}
	
	public void print(int offset) {
		for (int i = 0; i < offset; i++)
			System.out.print("   ");
		System.out.print("NODE: " + stateValue + ":" + runningLogProb + "\n");
		if (children != null)
			for (ViterbiTree child : children) {
				child.print(offset+1);
			}
	}

}
