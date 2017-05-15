package keyboard;

import static keyboard.Key.*;
import static keyboard.KeyLayout.COLEMAK;
import static keyboard.KeyLayout.DVORAK;
import static keyboard.KeyLayout.QWERTY;
import static keyboard.KeyLayout.ROTATION_13;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import combinatorics.Permutation;

/**
 * @author skeleton
 *
 */
public class AppleNumericMB110LLKeyboardMetricsImpl_Moran 
							implements KeyboardMetrics {
	private List<Key> vertexLabels;
	private int[][] adjacencyMatrix;
	private int[][] distanceMatrix;
	private Key homeKey;
	
	private static Map<KeyLayout, Key> keyLayoutToHomeKeyMap;
	private static Map<KeyLayout, Map<Key, Set<Key>>> keyLayoutToKeyToNeighborMapMap;
	
	static
	{
		keyLayoutToHomeKeyMap = new HashMap<KeyLayout, Key>();
		keyLayoutToHomeKeyMap.put(QWERTY, J);
		keyLayoutToHomeKeyMap.put(DVORAK, H);
		keyLayoutToHomeKeyMap.put(COLEMAK, N);
		keyLayoutToHomeKeyMap.put(ROTATION_13, W);
		
		keyLayoutToKeyToNeighborMapMap = new HashMap<KeyLayout, Map<Key, Set<Key>>>();
		Map<Key, Set<Key>> keyToNeighborMap_QWERTY  = getKeyToNeighborMap_QWERTY();
		Map<Key, Set<Key>> keyToNeighborMap_DVORAK  = getKeyToNeighborMap_DVORAK();
		Map<Key, Set<Key>> keyToNeighborMap_COLEMAK = getKeyToNeighborMap_COLEMAK();
		Map<Key, Set<Key>> keyToNeighborMap_ROT_13  = getKeyToNeighborMap_ROT_13();
		
		keyLayoutToKeyToNeighborMapMap.put(QWERTY, keyToNeighborMap_QWERTY); // (layout, map)
		
		keyLayoutToKeyToNeighborMapMap.put(DVORAK, keyToNeighborMap_DVORAK);
		keyLayoutToKeyToNeighborMapMap.put(COLEMAK, keyToNeighborMap_COLEMAK);
		keyLayoutToKeyToNeighborMapMap.put(ROTATION_13, keyToNeighborMap_ROT_13);
	}
	
	public AppleNumericMB110LLKeyboardMetricsImpl_Moran(KeyLayout keyLayout)
	{
		this.homeKey = keyLayoutToHomeKeyMap.get(keyLayout);
		Map<Key, Set<Key>> keyToNeighborsMap = keyLayoutToKeyToNeighborMapMap.get(keyLayout);
		init(keyToNeighborsMap, new ArrayList<Key>(keyToNeighborsMap.keySet()));
		
	}
	
	public void init(Map<Key, Set<Key>> physicalKeyToNeighborsMap, List<Key> vertexLabels)
	{
		this.vertexLabels = vertexLabels;
		//System.out.println(vertexLabels);
		this.adjacencyMatrix = getAdjacencyMatrix(physicalKeyToNeighborsMap, vertexLabels);
		this.distanceMatrix = getDistanceMatrix(adjacencyMatrix);
	}
	
	private static int[][] getAdjacencyMatrix(Map<Key, Set<Key>> physicalKeyToNeighborsMap, List<Key> vertexLabels)
	{
		assert physicalKeyToNeighborsMap.keySet().equals(new HashSet<Key>(vertexLabels)) : "vertexLabels inconsistent with physicalKeyToNeighborsMap! : vertexLabels = " + vertexLabels + " physicalKeyToNeighborsMap.keySet() = " + physicalKeyToNeighborsMap.keySet();
		final int SIZE = physicalKeyToNeighborsMap.keySet().size();
		int[][] adjacencyMatrix = new int[SIZE][SIZE];
		
		for(int row = 0; row < SIZE; row++)
		{
			for(int column = 0; column < SIZE; column++)
			{
				Key letterToGetFromNeighborMap = vertexLabels.get(row);
				Key letterToCheckInNeighborMap = vertexLabels.get(column);
				//System.out.println(vertexLabels);
				
				if(physicalKeyToNeighborsMap.get(letterToGetFromNeighborMap).contains(letterToCheckInNeighborMap))
				{
					adjacencyMatrix[row][column] = 1;
				}                            
				else
				{
					adjacencyMatrix[row][column] = 0;
				}
			}

		}
		
		return adjacencyMatrix;
	}
	
	//Matrix multiplication
	private static int[][] multiply(int[][] A, int[][] B)
	{
		int rowCount_A = A.length;
		assert rowCount_A > 0 : "rowCount_A = 0!";
		int columnCount_A = A[0].length;
		int rowCount_B = B.length;
		assert rowCount_B > 0 : "rowCount_B = 0!";
		int columnCount_B = B[0].length;
		assert columnCount_A == rowCount_B : "columnCount_A = " + columnCount_A + " <> " + rowCount_B + " = rowCount_B!";
		
		int[][] C = new int[rowCount_A][columnCount_B];
        for (int i = 0; i < rowCount_A; i++)
            for (int j = 0; j < columnCount_B; j++)
                for (int k = 0; k < columnCount_A; k++)
                    C[i][j] += A[i][k] * B[k][j];
        
        return C;
	}
	
	c
	private static Set<Key> getKeySet(char character)
	{
		List<Key> keyList = Arrays.asList(Key.values());
		Set<Key> characterProducingKeysSet = new HashSet<Key>();
		for(int i = 0; i < keyList.size(); i++)
		{
			Key key = keyList.get(i);
			assert key != null : "key is null!";
			boolean keyProducesCharacter = (key.getNormalCharacter() != null && key.getNormalCharacter() == character) || (key.getShiftModifiedCharacter() != null && key.getShiftModifiedCharacter() == character);
			if(keyProducesCharacter) characterProducingKeysSet.add(key);
		}
		return characterProducingKeysSet;
	}
	
	private static Map<Key, Set<Key>> getKeyToNeighborMap_QWERTY()
	{
		Map<Key, Set<Key>> keyToNeighborSetMap = new HashMap<Key, Set<Key>>();
		
		//top row
		keyToNeighborSetMap.put(Key.BACKTICK, getSet(Key.TAB, Key.ONE));
        keyToNeighborSetMap.put(Key.ONE, getSet(Key.BACKTICK, Key.TAB, Key.Q, Key.TWO));
        keyToNeighborSetMap.put(Key.TWO, getSet(Key.ONE, Key.Q, Key.W, Key.THREE));
        keyToNeighborSetMap.put(Key.THREE, getSet(Key.TWO, Key.W, Key.E, Key.FOUR));
        keyToNeighborSetMap.put(Key.FOUR, getSet(Key.THREE,Key.E, Key.R, Key.FIVE));
        keyToNeighborSetMap.put(Key.FIVE, getSet(Key.FOUR, Key.R, Key.T ,Key.SIX));
        keyToNeighborSetMap.put(Key.SIX, getSet(Key.FIVE, Key.T, Key.Y, Key.SEVEN));
        keyToNeighborSetMap.put(Key.SEVEN, getSet(Key.SIX,Key.Y, Key.U, Key.EIGHT));
        keyToNeighborSetMap.put(Key.EIGHT, getSet(Key.SEVEN, Key.U, Key.I, Key.NINE));
        keyToNeighborSetMap.put(Key.NINE, getSet(Key.EIGHT, Key.I, Key.O, Key.ZERO));
        keyToNeighborSetMap.put(Key.ZERO, getSet(Key.NINE, Key.O, Key.P, Key.MINUS));
        keyToNeighborSetMap.put(Key.MINUS, getSet(Key.ZERO, Key.P, Key.LEFT_BRACKET, Key.EQUALS));
        keyToNeighborSetMap.put(Key.EQUALS, getSet(Key.MINUS, Key.LEFT_BRACKET, Key.RIGHT_BRACKET));
        keyToNeighborSetMap.put(Key.RIGHT_BRACKET, getSet(Key.EQUALS, Key.LEFT_BRACKET, Key.TICK, Key.RETURN, Key.BACKSLASH));
        keyToNeighborSetMap.put(Key.BACKSLASH, getSet(Key.RIGHT_BRACKET, Key.RETURN));
        
        //second row
        keyToNeighborSetMap.put(Key.TAB, getSet(Key.BACKTICK, Key.ONE, Key.Q));
        keyToNeighborSetMap.put(Key.Q, getSet(Key.ONE, Key.TWO, Key.TAB, Key.A, Key.W));
        keyToNeighborSetMap.put(Key.W, getSet(Key.TWO, Key.Q, Key.A, Key.S, Key.E, Key.THREE));
        keyToNeighborSetMap.put(Key.E, getSet(Key.THREE, Key.W, Key.S, Key.D, Key.R, Key.FOUR)); 
        keyToNeighborSetMap.put(Key.R, getSet(Key.FOUR, Key.E, Key.D, Key.F, Key.T, Key.FIVE));
        keyToNeighborSetMap.put(Key.T, getSet(Key.FIVE, Key.R, Key.F, Key.G, Key.Y, Key.SIX));
        keyToNeighborSetMap.put(Key.Y, getSet(Key.SIX, Key.T, Key.G, Key.H, Key.U, Key.SEVEN));
        keyToNeighborSetMap.put(Key.U, getSet(Key.SEVEN, Key.Y, Key.H, Key.J, Key.I, Key.EIGHT));
        keyToNeighborSetMap.put(Key.I, getSet(Key.EIGHT, Key.U, Key.J, Key.K, Key.O, Key.NINE));
        keyToNeighborSetMap.put(Key.O, getSet(Key.NINE, Key.I, Key.K, Key.L, Key.P, Key.ZERO));
        keyToNeighborSetMap.put(Key.P, getSet(Key.ZERO, Key.O, Key.L, Key.P));
        keyToNeighborSetMap.put(Key.LEFT_BRACKET, getSet(Key.MINUS, Key.P, Key.SEMICOLON, Key.TICK, Key.RIGHT_BRACKET, Key.EQUALS));
        
        //third row
        keyToNeighborSetMap.put(Key.A, getSet(Key.Q, Key.SHIFT_1,Key.Z, Key.S, Key.W));
        keyToNeighborSetMap.put(Key.S, getSet(Key.W, Key.A, Key.Z, Key.X, Key.D, Key.E));
        keyToNeighborSetMap.put(Key.D, getSet(Key.E, Key.S, Key.X, Key.C, Key.F, Key.R));
        keyToNeighborSetMap.put(Key.F, getSet(Key.R, Key.D, Key.C, Key.V, Key.G, Key.T));
        keyToNeighborSetMap.put(Key.G, getSet(Key.T, Key.F, Key.V, Key.B, Key.H, Key.Y));
        keyToNeighborSetMap.put(Key.H, getSet(Key.Y, Key.G, Key.B, Key.N, Key.J, Key.U));
        keyToNeighborSetMap.put(Key.J, getSet(Key.U, Key.H, Key.N, Key.M, Key.K, Key.K, Key.I));
        keyToNeighborSetMap.put(Key.K, getSet(Key.I, Key.J, Key.M, Key.COMMA, Key.L, Key.O));
        keyToNeighborSetMap.put(Key.L, getSet(Key.O, Key.K, Key.COMMA, Key.PERIOD, Key.SEMICOLON, Key.P));
        keyToNeighborSetMap.put(Key.SEMICOLON, getSet(Key.P, Key.L, Key.PERIOD, Key.FORESLASH, Key.TICK, Key.LEFT_BRACKET));
        keyToNeighborSetMap.put(Key.TICK, getSet(Key.LEFT_BRACKET, Key.SEMICOLON, Key.FORESLASH, Key.SHIFT_2, Key.RETURN, Key.RIGHT_BRACKET));
        keyToNeighborSetMap.put(Key.RETURN, getSet(Key.RIGHT_BRACKET, Key.TICK, Key.SHIFT_2, Key.BACKSLASH));
        
        //fourth row
        keyToNeighborSetMap.put(Key.SHIFT_1, getSet(Key.A, Key.Z));
        keyToNeighborSetMap.put(Key.Z, getSet(Key.A, Key.SHIFT_1, Key.X, Key.S));
        keyToNeighborSetMap.put(Key.X, getSet(Key.S, Key.Z, Key.C, Key.D));
        keyToNeighborSetMap.put(Key.C, getSet(Key.D, Key.X, Key.SPACEBAR_1, Key.V, Key.F));
        keyToNeighborSetMap.put(Key.V, getSet(Key.F, Key.C, Key.SPACEBAR_2, Key.B, Key.G));
        keyToNeighborSetMap.put(Key.B, getSet(Key.G, Key.V, Key.SPACEBAR_3, Key.N, Key.H));
        keyToNeighborSetMap.put(Key.N, getSet(Key.H, Key.B, Key.SPACEBAR_4, Key.M, Key.J));
        keyToNeighborSetMap.put(Key.M, getSet(Key.J, Key.N, Key.SPACEBAR_5, Key.COMMA, Key.K));
        keyToNeighborSetMap.put(Key.COMMA, getSet(Key.K, Key.M, Key.PERIOD, Key.L));
        keyToNeighborSetMap.put(Key.PERIOD, getSet(Key.L, Key.COMMA, Key.FORESLASH, Key.SEMICOLON));
        keyToNeighborSetMap.put(Key.FORESLASH, getSet(Key.SEMICOLON, Key.PERIOD, Key.SHIFT_2, Key.TICK));
        keyToNeighborSetMap.put(Key.SHIFT_2, getSet(Key.TICK, Key.FORESLASH, Key.RETURN));
        
        //spacebars
        keyToNeighborSetMap.put(Key.SPACEBAR_1, getSet(Key.C));
        keyToNeighborSetMap.put(Key.SPACEBAR_2, getSet(Key.V));
        keyToNeighborSetMap.put(Key.SPACEBAR_3, getSet(Key.B));
        keyToNeighborSetMap.put(Key.SPACEBAR_4, getSet(Key.N));
        keyToNeighborSetMap.put(Key.SPACEBAR_5, getSet(Key.M));

        
		return keyToNeighborSetMap;
	}


	
	private static Set<Key> getSet(Key... keys)
	{
		return new HashSet<Key>(Arrays.asList(keys));
	}
	
	//Thanks anonymous who sent me the maps below. Sorry Dr. Kart. :)
	
	private static Map<Key, Set<Key>> getKeyToNeighborMap_DVORAK()
	{
		Map<Key, Set<Key>> keyToNeighborSetMap = new HashMap<Key, Set<Key>>();
		
		//top row
		keyToNeighborSetMap.put(Key.BACKTICK, getSet(ONE, TAB));
		keyToNeighborSetMap.put(Key.ONE, getSet(TWO, BACKTICK, TAB, TICK));
		keyToNeighborSetMap.put(Key.TWO, getSet(THREE, ONE, TICK, COMMA));
		keyToNeighborSetMap.put(Key.THREE, getSet(FOUR, TWO, COMMA, PERIOD));
		keyToNeighborSetMap.put(Key.FOUR, getSet(FIVE, THREE, PERIOD, P));
		keyToNeighborSetMap.put(Key.FIVE, getSet(SIX, FOUR, P, Y));
		keyToNeighborSetMap.put(Key.SIX, getSet(SEVEN, FIVE, Y, F));
		keyToNeighborSetMap.put(Key.SEVEN, getSet(EIGHT, SIX, F, G));
		keyToNeighborSetMap.put(Key.EIGHT, getSet(NINE, SEVEN, G, C));
		keyToNeighborSetMap.put(Key.NINE, getSet(ZERO, EIGHT, C, R));
		keyToNeighborSetMap.put(Key.ZERO, getSet(LEFT_BRACKET, NINE, R, L));
		keyToNeighborSetMap.put(Key.LEFT_BRACKET, getSet(RIGHT_BRACKET, ZERO, L, FORESLASH));
		keyToNeighborSetMap.put(Key.RIGHT_BRACKET, getSet(LEFT_BRACKET, FORESLASH, EQUALS));
			
		//second top
		keyToNeighborSetMap.put(Key.TAB, getSet(TICK, ONE, BACKTICK));
		keyToNeighborSetMap.put(Key.TICK, getSet(COMMA, TWO, ONE, TAB, A));
		keyToNeighborSetMap.put(Key.COMMA, getSet(PERIOD, THREE, TWO, TICK, A, O));
		keyToNeighborSetMap.put(Key.PERIOD, getSet(P, FOUR, THREE, COMMA, P, E));
		keyToNeighborSetMap.put(Key.P, getSet(Y, FIVE, FOUR, PERIOD, E, U));
		keyToNeighborSetMap.put(Key.Y, getSet(F, SIX, FIVE, P, U, I));
		keyToNeighborSetMap.put(Key.F, getSet(G, SEVEN, SIX, Y, I, D));
		keyToNeighborSetMap.put(Key.G, getSet(C, EIGHT, SEVEN, F, D, H));
		keyToNeighborSetMap.put(Key.C, getSet(R, NINE, EIGHT, G, H, T));
		keyToNeighborSetMap.put(Key.R, getSet(L, ZERO, NINE, C, T, N));
		keyToNeighborSetMap.put(Key.L, getSet(FORESLASH, LEFT_BRACKET, ZERO, R, N, S));
		keyToNeighborSetMap.put(Key.FORESLASH, getSet(EQUALS, RIGHT_BRACKET, LEFT_BRACKET, L, S, MINUS));
		keyToNeighborSetMap.put(Key.EQUALS, getSet(BACKSLASH, RIGHT_BRACKET, FORESLASH, MINUS, RETURN));
		keyToNeighborSetMap.put(Key.BACKSLASH, getSet(EQUALS, RETURN));
		
		//third row
		keyToNeighborSetMap.put(Key.A, getSet(O, COMMA, TICK, SHIFT_1, SEMICOLON));
		keyToNeighborSetMap.put(Key.O, getSet(E, PERIOD, COMMA, A, SEMICOLON, Q));
		keyToNeighborSetMap.put(Key.E, getSet(U, P, PERIOD, O, Q, J));
		keyToNeighborSetMap.put(Key.U, getSet(I, Y, P, E, J, K));
		keyToNeighborSetMap.put(Key.I, getSet(D, F, Y, U, K, X));
		keyToNeighborSetMap.put(Key.D, getSet(H, G, F, I,X,B));
		keyToNeighborSetMap.put(Key.H, getSet(T, C, G, D,B,M));
		keyToNeighborSetMap.put(Key.T, getSet(N, R, C, H, M, W));
		keyToNeighborSetMap.put(Key.N, getSet(S, L, R, T, W, V));
		keyToNeighborSetMap.put(Key.S, getSet(MINUS, FORESLASH, L, N, V, Z));
		keyToNeighborSetMap.put(Key.MINUS, getSet(RETURN, EQUALS, FORESLASH, S, Z, SHIFT_2));
		keyToNeighborSetMap.put(Key.RETURN, getSet(BACKSLASH, EQUALS, MINUS, SHIFT_2));
		
		
		//fourth row
		keyToNeighborSetMap.put(Key.SHIFT_1, getSet(SEMICOLON, A));
		keyToNeighborSetMap.put(Key.SEMICOLON, getSet(Q, O, A, SHIFT_1));
		keyToNeighborSetMap.put(Key.Q, getSet(J, E, O, SEMICOLON));
		keyToNeighborSetMap.put(Key.J, getSet(K, U, E, Q, SPACEBAR_1));
		keyToNeighborSetMap.put(Key.K, getSet(X, I, U, J, SPACEBAR_2));
		keyToNeighborSetMap.put(Key.X, getSet(B, D, I, K, SPACEBAR_3));
		keyToNeighborSetMap.put(Key.B, getSet(M, H, D, X, SPACEBAR_4));
		keyToNeighborSetMap.put(Key.M, getSet(W, T, H, B, SPACEBAR_5));
		keyToNeighborSetMap.put(Key.W, getSet(V, N, T, M));
		keyToNeighborSetMap.put(Key.V, getSet(Z, S, N, W));
		keyToNeighborSetMap.put(Key.Z, getSet(SHIFT_2, MINUS, S, V));
		keyToNeighborSetMap.put(Key.SHIFT_2, getSet(RETURN, MINUS, Z));

		//spacebars
		keyToNeighborSetMap.put(Key.SPACEBAR_1, getSet(Key.J));
		keyToNeighborSetMap.put(Key.SPACEBAR_2, getSet(Key.K));
		keyToNeighborSetMap.put(Key.SPACEBAR_3, getSet(B));
		keyToNeighborSetMap.put(Key.SPACEBAR_4, getSet(Key.B));
		keyToNeighborSetMap.put(Key.SPACEBAR_5, getSet(Key.M));

		
		return keyToNeighborSetMap;
	}
	
	
	private static Map<Key, Set<Key>> getKeyToNeighborMap_COLEMAK()
	{
		Map<Key, Set<Key>> keyToNeighborSetMap = new HashMap<Key, Set<Key>>();
			
		//top row
		keyToNeighborSetMap.put(Key.BACKTICK, getSet(ONE, TAB));
		keyToNeighborSetMap.put(Key.ONE, getSet(TWO, Q, TAB, BACKTICK));
		keyToNeighborSetMap.put(Key.TWO, getSet(THREE, ONE, W, Q));
		keyToNeighborSetMap.put(Key.THREE, getSet(FOUR, TWO, COMMA, PERIOD));
		keyToNeighborSetMap.put(Key.FOUR, getSet(FIVE, THREE, P, F));
		keyToNeighborSetMap.put(Key.FIVE, getSet(SIX, FOUR, G, P));
		keyToNeighborSetMap.put(Key.SIX, getSet(SEVEN, FIVE, J, G));
		keyToNeighborSetMap.put(Key.SEVEN, getSet(EIGHT, SIX, L, J));
		keyToNeighborSetMap.put(Key.EIGHT, getSet(NINE, SEVEN, U, L));
		keyToNeighborSetMap.put(Key.NINE, getSet(ZERO, EIGHT, Y, U));
		keyToNeighborSetMap.put(Key.ZERO, getSet(MINUS, NINE, SEMICOLON, Y));
		keyToNeighborSetMap.put(Key.MINUS, getSet(EQUALS, ZERO, LEFT_BRACKET, SEMICOLON));
		keyToNeighborSetMap.put(Key.EQUALS, getSet(MINUS, BACKSLASH, RIGHT_BRACKET));
		
		//second top row
		keyToNeighborSetMap.put(Key.TAB, getSet(Q, ONE, BACKTICK));
		keyToNeighborSetMap.put(Key.Q, getSet(W, TWO, ONE, TAB, A));
		keyToNeighborSetMap.put(Key.W, getSet(F, THREE, TWO, Q, A, R));
		keyToNeighborSetMap.put(Key.F, getSet(P, FOUR, THREE, W, R, S));
		keyToNeighborSetMap.put(Key.P, getSet(G, FIVE, FOUR, F, S, T));
		keyToNeighborSetMap.put(Key.G, getSet(J, SIX, FIVE, P, T, D));
		keyToNeighborSetMap.put(Key.J, getSet(L, SEVEN, SIX, G, D, H));
		keyToNeighborSetMap.put(Key.L, getSet(U, EIGHT, SEVEN, J, H, N));
		keyToNeighborSetMap.put(Key.U, getSet(Y, NINE, EIGHT, L, N, E));
		keyToNeighborSetMap.put(Key.Y, getSet(SEMICOLON, ZERO, NINE, U, E, I));
		keyToNeighborSetMap.put(Key.SEMICOLON, getSet(LEFT_BRACKET, MINUS, ZERO, Y, I, O));
		keyToNeighborSetMap.put(Key.LEFT_BRACKET, getSet(RIGHT_BRACKET, EQUALS, MINUS, SEMICOLON, O, TICK));
		keyToNeighborSetMap.put(Key.RIGHT_BRACKET, getSet(BACKSLASH, EQUALS, LEFT_BRACKET, TICK, RETURN));
		keyToNeighborSetMap.put(Key.BACKSLASH, getSet(RIGHT_BRACKET, RETURN));

		//third top row
		keyToNeighborSetMap.put(Key.A, getSet(R, W, Q, SHIFT_1, Z));
		keyToNeighborSetMap.put(Key.R, getSet(S, F, W, A, Z, X));
		keyToNeighborSetMap.put(Key.S, getSet(T, P, F, R, X, C));
		keyToNeighborSetMap.put(Key.T, getSet(D, G, P, S, C, V));
		keyToNeighborSetMap.put(Key.D, getSet(H, J, G, T, V, B));
		keyToNeighborSetMap.put(Key.H, getSet(N, L, J, D, B, K));
		keyToNeighborSetMap.put(Key.N, getSet(E, U, L, H, K, M));
		keyToNeighborSetMap.put(Key.E, getSet(I, Y, U, N, M, COMMA));
		keyToNeighborSetMap.put(Key.I, getSet(O, SEMICOLON, Y, E, COMMA, PERIOD));
		keyToNeighborSetMap.put(Key.O, getSet(TICK, LEFT_BRACKET, SEMICOLON, I, PERIOD, FORESLASH, SHIFT_2));
		keyToNeighborSetMap.put(Key.TICK, getSet(RETURN, RIGHT_BRACKET, LEFT_BRACKET, O, FORESLASH));
		keyToNeighborSetMap.put(Key.RETURN, getSet(BACKSLASH, RIGHT_BRACKET, TICK, SHIFT_2));

		//fourth row
		keyToNeighborSetMap.put(Key.SHIFT_1, getSet(Z, A));
		keyToNeighborSetMap.put(Key.Z, getSet(X, R, A, SHIFT_1));
		keyToNeighborSetMap.put(Key.X, getSet(C, S, R, Z));
		keyToNeighborSetMap.put(Key.C, getSet(V, T, S, X, SPACEBAR_1));
		keyToNeighborSetMap.put(Key.V, getSet(B, D, T, C, SPACEBAR_2));
		keyToNeighborSetMap.put(Key.B, getSet(K, H, D, V, SPACEBAR_3));
		keyToNeighborSetMap.put(Key.K, getSet(M, N, H, B, SPACEBAR_4));
		keyToNeighborSetMap.put(Key.M, getSet(COMMA, E, N, K, SPACEBAR_5));
		keyToNeighborSetMap.put(Key.COMMA, getSet(PERIOD, I, E, M));
		keyToNeighborSetMap.put(Key.PERIOD, getSet(FORESLASH, O, I, COMMA));
		keyToNeighborSetMap.put(Key.FORESLASH, getSet(SHIFT_2, TICK, O, PERIOD));
		keyToNeighborSetMap.put(Key.SHIFT_2, getSet(RETURN, TICK, FORESLASH));
	
		//spacebars
		keyToNeighborSetMap.put(Key.SPACEBAR_1, getSet(C));
		keyToNeighborSetMap.put(Key.SPACEBAR_2, getSet(V));
		keyToNeighborSetMap.put(Key.SPACEBAR_3, getSet(B));
		keyToNeighborSetMap.put(Key.SPACEBAR_4, getSet(K));
		keyToNeighborSetMap.put(Key.SPACEBAR_5, getSet(M));
			
			
		return keyToNeighborSetMap;
	}
	
	private static Map<Key, Set<Key>> getKeyToNeighborMap_ROT_13()
	{
		Map<Key, Set<Key>> keyToNeighborSetMap = new HashMap<Key, Set<Key>>();
			keyToNeighborSetMap.put(Key.BACKTICK, getSet(TAB, ONE));
			keyToNeighborSetMap.put(Key.TAB, getSet(ONE, BACKTICK,D));
			keyToNeighborSetMap.put(Key.ONE, getSet(TAB, BACKTICK, D, TWO));
			keyToNeighborSetMap.put(Key.D, getSet(TAB, ONE, TWO, N, J));
			keyToNeighborSetMap.put(Key.SHIFT_1, getSet(N, M));
			keyToNeighborSetMap.put(Key.N, getSet(SHIFT_1, M, F, J, D));
			keyToNeighborSetMap.put(Key.M, getSet(SHIFT_1, N, K, F));
			keyToNeighborSetMap.put(Key.TWO, getSet(ONE, D, J, THREE));
			keyToNeighborSetMap.put(Key.J, getSet(TWO, D, N, F, R, THREE));
			keyToNeighborSetMap.put(Key.F, getSet(N, M, K, Q, R, J));
			keyToNeighborSetMap.put(Key.K, getSet(M, F, Q, P));
			keyToNeighborSetMap.put(Key.THREE, getSet(TWO, J, R, FOUR));
			keyToNeighborSetMap.put(Key.R, getSet(THREE, J, F, Q, E, FOUR));
			keyToNeighborSetMap.put(Key.Q, getSet(R, F, K, P, S, E));
			keyToNeighborSetMap.put(Key.P, getSet(K, Q, S, I, SPACEBAR_1));
			keyToNeighborSetMap.put(Key.SPACEBAR_1, getSet(Key.P));
			keyToNeighborSetMap.put(Key.FOUR, getSet(THREE, E, R, FIVE));
			keyToNeighborSetMap.put(Key.E, getSet(FOUR, R, Q, S, G, FIVE));
			keyToNeighborSetMap.put(Key.S, getSet(Q, P, I, T, G, E));
			keyToNeighborSetMap.put(Key.I, getSet(P, S, T, O, SPACEBAR_2));
			keyToNeighborSetMap.put(Key.SPACEBAR_2, getSet(Key.I));
			keyToNeighborSetMap.put(Key.FIVE, getSet(FOUR, E, G, SIX));
			keyToNeighborSetMap.put(Key.G, getSet(FIVE, E, S, T, L, SIX));
			keyToNeighborSetMap.put(Key.T, getSet(G, S, I, O, U, L));
			keyToNeighborSetMap.put(Key.O, getSet(I, T, U, A, SPACEBAR_3));
			keyToNeighborSetMap.put(Key.SPACEBAR_3, getSet(O));
			keyToNeighborSetMap.put(Key.SIX, getSet(FIVE, G, L, SEVEN));
			keyToNeighborSetMap.put(Key.L, getSet(SIX, G, T, U, H, SEVEN));
			keyToNeighborSetMap.put(Key.U, getSet(L, T, O, A, W, H));
			keyToNeighborSetMap.put(Key.A, getSet(O,U, W, Z, SPACEBAR_4));
			keyToNeighborSetMap.put(Key.SPACEBAR_4, getSet(Key.A));
			keyToNeighborSetMap.put(Key.SEVEN, getSet(SIX, L, H, EIGHT));
			keyToNeighborSetMap.put(Key.H, getSet(SEVEN, L, U, W, V, EIGHT));
			keyToNeighborSetMap.put(Key.W, getSet(H, U, A, Z, X, V));
			keyToNeighborSetMap.put(Key.Z, getSet(A, W, X, COMMA, SPACEBAR_5));
			keyToNeighborSetMap.put(Key.SPACEBAR_5, getSet(Key.Z));
			keyToNeighborSetMap.put(Key.EIGHT, getSet(SEVEN, H, V, NINE));
			keyToNeighborSetMap.put(Key.V, getSet(EIGHT, H, W, X, B, NINE));
			keyToNeighborSetMap.put(Key.X, getSet(V, W, Z, COMMA, Y, B));
			keyToNeighborSetMap.put(Key.COMMA, getSet(Z, X, Y, PERIOD));
			keyToNeighborSetMap.put(Key.NINE, getSet(EIGHT, V, B, ZERO));
			keyToNeighborSetMap.put(Key.B, getSet(NINE, V, X, Y, C, ZERO));
			keyToNeighborSetMap.put(Key.Y, getSet(C, B, COMMA, PERIOD, SEMICOLON, X));
			keyToNeighborSetMap.put(Key.PERIOD, getSet(COMMA, Y, SEMICOLON, FORESLASH));
			keyToNeighborSetMap.put(Key.ZERO, getSet(NINE, B, V, MINUS));
			keyToNeighborSetMap.put(Key.C, getSet(ZERO, B, Y, SEMICOLON, LEFT_BRACKET, MINUS));
			keyToNeighborSetMap.put(Key.SEMICOLON, getSet(Y, C, PERIOD, FORESLASH, TICK, LEFT_BRACKET));
			keyToNeighborSetMap.put(Key.FORESLASH, getSet(PERIOD, SEMICOLON, TICK, SHIFT_2));
			keyToNeighborSetMap.put(Key.MINUS, getSet(ZERO, C, LEFT_BRACKET, EQUALS));
			keyToNeighborSetMap.put(Key.LEFT_BRACKET, getSet(MINUS, C, SEMICOLON, TICK, RIGHT_BRACKET, EQUALS));
			keyToNeighborSetMap.put(Key.TICK, getSet(LEFT_BRACKET, SEMICOLON, FORESLASH, SHIFT_2, RETURN, RIGHT_BRACKET));
			keyToNeighborSetMap.put(Key.SHIFT_2, getSet(FORESLASH, TICK, RETURN));
			keyToNeighborSetMap.put(Key.EQUALS, getSet(MINUS, LEFT_BRACKET, RIGHT_BRACKET));
			keyToNeighborSetMap.put(Key.RIGHT_BRACKET, getSet(EQUALS, LEFT_BRACKET, TICK, RETURN, BACKSLASH));
			keyToNeighborSetMap.put(Key.RETURN, getSet(BACKSLASH, RIGHT_BRACKET, TICK, SHIFT_2));
			keyToNeighborSetMap.put(Key.BACKSLASH, getSet(RIGHT_BRACKET, RETURN));

		return keyToNeighborSetMap;
	}

	
	/*
	private static Permutation_Skeleton<Key> getQWERTYToDvorakPermutation()
	{
		//Figure out what Permutation_Skeleton should be and QWERTYToDvorakPermutation if you want to use...
		//If you invent your own Permutation type, remember to name it Permutation_LastName...
	}
	
	private static Permutation_Skeleton<Key> getQWERTYToColemakPermutation()
	{
		//Figure out what Permutation_Skeleton should be and QWERTYToColemakPermutation if you want to use...
		//If you invent your own Permutation type, remember to name it Permutation_LastName...
	}
	
	private static Permutation_Skeleton<Key> getQWERTYToRotation13Permutation()
	{
		//Figure out what Permutation_Skeleton should be and QWERTYToRotation13Permutation if you want to use...
		//If you invent your own Permutation type, remember to name it Permuation_LastName...
	}
	
	private static <E> Map<E, Set<E>> applyPermutationToMap(Map<E, Set<E>> map, Permutation_Skeleton<E> permutation)
	{
		//Ex: map = {A -> {B, E}, B -> {A, C, D}, C -> {B}, D -> {B}, E -> {A}}, permutation(A) = B, permutation(B) = C, permutation(C) = A
		Map<E, Set<E>> newMap = new HashMap<E, Set<E>>();

		//DO SOMETHING HERE...

		//Ex: map = {C -> {B, E}, A -> {A, C, D}, B -> {B}, D -> {B}, E -> {A}}, permutation(A) = B, permutation(B) = C, permutation(C) = A

		//DO SOMETHING ELSE HERE...

		//Ex: map = {C -> {A, E}, A -> {C, B, D}, B -> {A}, D -> {A}, E -> {C}}, permutation(A) = B, permutation(B) = C, permutation(C) = A
		return newMap;
	}
	*/
}