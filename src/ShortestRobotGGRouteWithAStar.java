import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestRobotGGRouteWithAStar implements ShortestRobotGGRoute {
	private class Node implements Comparable<Node> {
		public RobotGG robotGG;
		public int currentDistance;
		public TileBoard tileBoard;
		public MoveDirection direction;
		public Node parent;
		public int level;
		
		public Node(RobotGG robotGG, TileBoard tileBoard, int currentDistance, MoveDirection direction, Node parent, int level) {
			this.tileBoard = tileBoard;
			this.robotGG = robotGG;
			this.currentDistance = currentDistance;
			this.direction = direction;
			this.parent = parent;
			this.level = level;
		}
		
		public boolean isSolution() {
			// Find tile with status true
			boolean hasTrueStatusTile = false;
			int i = 1;
			while (i <= tileBoard.getNumberOfRows() && !hasTrueStatusTile) {
				int j = 1;
				while (j <= tileBoard.getNumberOfColumns() && !hasTrueStatusTile) {
					if (tileBoard.getTileStatus(i, j)) {
						hasTrueStatusTile = true;
					} else {
						j++;
					}
				}
				if (!hasTrueStatusTile) {
					i++;
				}
			}
			
			return !hasTrueStatusTile;
		}
		
		public Node getParent() {
			return parent;
		}
		
		public Node getUpNode() {
			TileBoard newTileBoard = new TileBoard(tileBoard);
			RobotGG newRobotGG = new RobotGG(robotGG, newTileBoard);
			boolean canMove = newRobotGG.moveUp();
			if (canMove) {
				return new Node(newRobotGG, newTileBoard, currentDistance + 1, MoveDirection.UP, this, level + 1);
			} else {
				return null;
			}
		}
		
		public Node getDownNode() {
			TileBoard newTileBoard = new TileBoard(tileBoard);
			RobotGG newRobotGG = new RobotGG(robotGG, newTileBoard);
			boolean canMove = newRobotGG.moveDown();
			if (canMove) {
				return new Node(newRobotGG, newTileBoard, currentDistance + 1, MoveDirection.DOWN, this, level + 1);
			} else {
				return null;
			}
		}
		
		public Node getLeftNode() {
			TileBoard newTileBoard = new TileBoard(tileBoard);
			RobotGG newRobotGG = new RobotGG(robotGG, newTileBoard);
			boolean canMove = newRobotGG.moveLeft();
			if (canMove) {
				return new Node(newRobotGG, newTileBoard, currentDistance + 1, MoveDirection.LEFT, this, level + 1);
			} else {
				return null;
			}
		}
		
		public Node getRightNode() {
			TileBoard newTileBoard = new TileBoard(tileBoard);
			RobotGG newRobotGG = new RobotGG(robotGG, newTileBoard);
			boolean canMove = newRobotGG.moveRight();
			if (canMove) {
				return new Node(newRobotGG, newTileBoard, currentDistance + 1, MoveDirection.RIGHT, this, level + 1);
			} else {
				return null;
			}
		}
		
		public MoveDirection getDirection() {
			return direction;
		}
		
		public int getValue() {
			int robotRow = robotGG.getCurrentRow();
			int robotCol = robotGG.getCurrentColumn();
			int maxDistance = 0; // to black tile (true status tile)
			for (int i = 1; i <= tileBoard.getNumberOfRows(); i++) {
				for (int j = 1; j <= tileBoard.getNumberOfColumns(); j++) {
					if (tileBoard.getTileStatus(i, j)) {
						maxDistance = Math.max(maxDistance, Math.abs(robotRow - i) + Math.abs(robotCol - j));
					}
				}
			}
			
			return currentDistance + maxDistance;
		}
		
		public TileBoard getTileBoard() {
			return tileBoard;
		}
		
		public String getTrace() {
			if (parent == null) {
				return new StringBuilder()
					.append("(")
					.append(robotGG.getCurrentRow())
					.append(", ")
					.append(robotGG.getCurrentColumn())
					.append(") [")
					.append(getValue())
					.append("]")
					.toString();
			} else {
				return new StringBuilder(parent.getTrace())
					.append(" --")
					.append(getDirection())
					.append("--> ")
					.append("(")
					.append(robotGG.getCurrentRow())
					.append(", ")
					.append(robotGG.getCurrentColumn())
					.append(") [")
					.append(getValue())
					.append("]")
					.toString();
			}
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			} else if (obj instanceof Node) {
				Node node = (Node) obj;
				int robotRow = robotGG.getCurrentRow();
				int robotCol = robotGG.getCurrentColumn();
				return robotRow == node.robotGG.getCurrentRow() && robotCol == node.robotGG.getCurrentColumn() && tileBoard.equals(node.tileBoard);
			} else {
				return false;
			}
		}
		
		@Override
		public int compareTo(Node otherNode) {
			int myValue = getValue();
			int otherValue = otherNode.getValue();
			if (myValue < otherValue) {
				return -1;
			} else if (myValue == otherValue) {
				return 0;
			} else {
				return 1;
			}
		}
		
	}
	
	private RobotGG robotGG;
	private TileBoard tileBoard;
	
	public ShortestRobotGGRouteWithAStar(RobotGG robotGG) {
		this.robotGG = robotGG;
		this.tileBoard = robotGG.getTileBoard();
	}
	
	@Override
	public List<MoveDirection> getSolution() {
		PriorityQueue<Node> frontier = new PriorityQueue<Node>();
		frontier.add(new Node(robotGG, tileBoard, 0, null, null, 0));
		ArrayList<Node> visited = new ArrayList<Node>();
		// HashSet<Node> visited = new HashSet<Node>();
		Node currentSolution = null;
		int iterationNo = 1;
		boolean stopSearching = false;
		while (!frontier.isEmpty() && !stopSearching) {
			System.out.println("Iteration " + iterationNo);
			Node currentNode = frontier.poll();
			System.out.println(currentNode.getTrace());
			boolean worseNode = false;
			if (currentSolution != null) {
				worseNode = currentNode.getValue() >= currentSolution.getValue(); // currentNode >= currentSolution
			}
			
			if (!worseNode) {
				if (currentNode.isSolution()) {
					currentSolution = currentNode;
					System.out.println("Is a solution. Stop.");
					stopSearching = true;
					
				} else {
					// Expand
					Node upNode = currentNode.getUpNode();
					Node downNode = currentNode.getDownNode();
					Node leftNode = currentNode.getLeftNode();
					Node rightNode = currentNode.getRightNode();
					
					// Prioritize same direction
					MoveDirection currentDirection = currentNode.getDirection();
					if (currentDirection != null) {
						switch (currentNode.getDirection()) {
							case UP:
								if (upNode != null) {
									if (!visited.contains(upNode)) {
										frontier.add(upNode);
										System.out.println("Expand UP");
									}
								}
								break;
							
							case DOWN:
								if (downNode != null) {
									if (!visited.contains(downNode)) {
										frontier.add(downNode);
										System.out.println("Expand DOWN");
									}
								}
								break;
							
							case LEFT:
								if (leftNode != null) {
									if (!visited.contains(leftNode)) {
										frontier.add(leftNode);
										System.out.println("Expand LEFT");
									}
								}
								break;
								
							case RIGHT:
								if (rightNode != null) {
									if (!visited.contains(rightNode)) {
										frontier.add(rightNode);
										System.out.println("Expand RIGHT");
									}
								}
						}
					
					}
					
					if (upNode != null && currentDirection != MoveDirection.UP) {
						if (!visited.contains(upNode)) {
							frontier.add(upNode);
							System.out.println("Expand UP");
						}
					}
					
					if (downNode != null && currentDirection != MoveDirection.DOWN) {
						if (!visited.contains(downNode)) {
							frontier.add(downNode);
							System.out.println("Expand DOWN");
						}
					}
					
					if (leftNode != null && currentDirection != MoveDirection.LEFT) {
						if (!visited.contains(leftNode)) {
							frontier.add(leftNode);
							System.out.println("Expand LEFT");
						}
					}
					
					if (rightNode != null && currentDirection != MoveDirection.RIGHT) {
						if (!visited.contains(rightNode)) {
							frontier.add(rightNode);
							System.out.println("Expand RIGHT");
						}
						
					}
					
					System.out.println("Nothing left to expand.");
					
				}
				
			} // else:
			
			visited.add(currentNode);
			iterationNo++;
			
		}
		
		System.out.println("Node visited: " + visited.size() + "/" + (visited.size() + frontier.size()));
		
		ArrayList<MoveDirection> solution = new ArrayList<MoveDirection>();
		Node backtrackNode = currentSolution;
		while (backtrackNode.getDirection() != null) {
			solution.add(backtrackNode.getDirection());
			backtrackNode = backtrackNode.getParent();
		}

		Collections.reverse(solution);
		return solution;
		
	}
}
