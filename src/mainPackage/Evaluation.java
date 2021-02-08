package mainPackage;

import java.util.HashMap;

public class Evaluation {

	private HashMap<EvaluationCriterias, Integer> evaluationCriterias = null;

	enum EvaluationCriterias {
		HOLDING_1_UNIT, ENEMY_HOLDING_1_UNIT, NEED_THAT_CONTINENT, NO_BIG_THREATS_IN_NEIGHBOURHOOD,
		NO_BIG_THREATS_AT_BORDER, LEFT_WITH_0_UNITS, BIG_THREATS_IN_NEIGHBOURHOOD
	}

	public Evaluation() {
		assignEvaluationToEvaluationCriterias();
	}

	private void assignEvaluationToEvaluationCriterias() {
		evaluationCriterias = new HashMap<EvaluationCriterias, Integer>();

		evaluationCriterias.put(EvaluationCriterias.HOLDING_1_UNIT, 1);
		evaluationCriterias.put(EvaluationCriterias.ENEMY_HOLDING_1_UNIT, -1);
		evaluationCriterias.put(EvaluationCriterias.NO_BIG_THREATS_IN_NEIGHBOURHOOD, 5);
		evaluationCriterias.put(EvaluationCriterias.NO_BIG_THREATS_AT_BORDER, 4);
		evaluationCriterias.put(EvaluationCriterias.LEFT_WITH_0_UNITS, Integer.MIN_VALUE);
		evaluationCriterias.put(EvaluationCriterias.BIG_THREATS_IN_NEIGHBOURHOOD, -5);
		evaluationCriterias.put(EvaluationCriterias.NEED_THAT_CONTINENT, 3);
	}

	public int getEvaluationCriteria(EvaluationCriterias evaluationCriteria) {
		return this.evaluationCriterias.get(evaluationCriteria);
	}

}
