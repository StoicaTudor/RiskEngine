package mainPackage;

import java.util.ArrayList;

public class Minimax {

	private final Mission mission;
	private final int depth;
	private final Army playerToAnalyze; // this is the AI player
	private final Army enemyPlayer;
	private int minimumRiskToTake = 50;
	// this must be an integer between 0 and 100; it is used in decision making, by
	// answering to the question: which is the minimum probability for attacking the
	// AI will consider in a attacking scenario?
	// I set it up to a default value of 50

	public Minimax(int depth, Army playerToAnalyze, Army enemyPlayer, Mission mission) {

		this.depth = depth;
		this.playerToAnalyze = playerToAnalyze;
		this.enemyPlayer = enemyPlayer;
		this.mission = mission;
	}

	public double doMinimax(Position position, int currentDepth, double alpha, double beta, Army player) {

		position.computeChildrenOfPosition();
		ArrayList<Position> childrenPositions = position.getChildrenOfPosition();

		// position is a child of position; every time I enter this function, I
		// need to analyze the children of position
		// since I will use them in this method

		if (currentDepth == 0 || isGameOver()) {
			return position.evaluatePosition(this.mission);
		}

		if (player == playerToAnalyze) {

			double maxEvaluation = Integer.MIN_VALUE;

			for (Position currentPosition : childrenPositions) { // every child of position

				double currentEvaluation = doMinimax(currentPosition, currentDepth - 1, alpha, beta, enemyPlayer);
				maxEvaluation = Math.max(maxEvaluation, currentEvaluation);
				alpha = Math.max(alpha, currentEvaluation);

				if (currentEvaluation >= maxEvaluation) {
					position.bestNextPosition = currentPosition;
					// assign the best next position, when a new max has been found
					// I think this should be done after the alpha beta pruning has been tested
				}

				if (beta <= alpha) {
					break;
				}

//				if (currentEvaluation >= maxEvaluation) {
//					position.bestNextPosition = currentPosition;
//					// assign the best next position, when a new max has been found
//					// I think this should be done after the alpha beta pruning has been tested
//				}
			}

			return maxEvaluation;

		} else {

			double minEvaluation = Integer.MAX_VALUE;

			for (Position currentPosition : childrenPositions) {

				double currentEvaluation = doMinimax(currentPosition, currentDepth - 1, alpha, beta, playerToAnalyze);
				minEvaluation = Math.min(minEvaluation, currentEvaluation);
				beta = Math.min(beta, currentEvaluation);

				if (beta <= alpha) {
					break;
				}
			}
			return minEvaluation;
		}
	}

	private void printRegions(Position position) {

		for (GameRegion currentRegion : position.gameRegionsOccupiedByPlayer) {
			System.out.println(currentRegion.regionName + " " + currentRegion.nrUnits);
		}
	}

	private boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	public Army getPlayerToAnalyze() {
		return this.playerToAnalyze;
	}
}
