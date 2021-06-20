package com.company;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class Tests {

    @Test
    public void boardCreationTest() {
        Board board = new Board();
        Random random = new Random();
        assertEquals(Color.GREEN, board.getColor(random.nextInt(8), random.nextInt(8)));

        assertEquals(64, board.getHoleDisks(Color.BLACK) + board.getHoleDisks(Color.WHITE));
        assertEquals(32, board.getHoleDisks(Color.BLACK));
        assertEquals(32, board.getHoleDisks(Color.WHITE));

        assertFalse(board.changeDiskColor(random.nextInt(8), random.nextInt(8)));
    }

    @Test
    public void wrongValuesBoardTest() {
        Board board = new Board();

        assertEquals(-1, board.getHoleDisks(Color.GREEN));
        assertNull(board.peekDiskFromHole(Color.GREEN));
        assertNull(board.popDiskFromHole(Color.GREEN));
        assertFalse(board.put(0, 0, null, true));

        Disk disk = board.popDiskFromHole(Color.WHITE);
        assertFalse(board.put(-1, 0, disk, true));
        assertFalse(board.put(0, -1, disk, true));
        assertFalse(board.put(4, 10, disk, true));
        assertFalse(board.put(8, 0, disk, true));

        assertFalse(board.changeDiskColor(-1, 0));
        assertFalse(board.changeDiskColor(9, 1));
        assertFalse(board.changeDiskColor(2, -3));
        assertFalse(board.changeDiskColor(0, 8));

        assertNull(board.getColor(-1, 0));
        assertNull(board.getColor(9, 1));
        assertNull(board.getColor(2, -3));
        assertNull(board.getColor(0, 8));
    }

    @Test
    public void popPeekAndPushDiskTest() {
        Board board = new Board();

        board.peekDiskFromHole(Color.WHITE);
        assertEquals(32, board.getHoleDisks(Color.WHITE));

        Disk disk = board.popDiskFromHole(Color.WHITE);
        assertEquals(31, board.getHoleDisks(Color.WHITE));

        board.pushDiskBackToHole(disk);
        assertEquals(32, board.getHoleDisks(Color.WHITE));
    }

    @Test
    public void putDiskTest() {
        Board board = new Board();
        Disk disk = board.popDiskFromHole(Color.WHITE);

        board.put(0, 0, disk, false);
        assertEquals(Color.GREEN, board.getColor(0, 0));

        board.put(0, 0, disk, true);
        assertEquals(Color.WHITE, board.getColor(0, 0));

        assertFalse(board.put(0, 0, disk, true));
        assertFalse(board.put(0, 0, disk, false));

        Disk disk1 = board.popDiskFromHole(Color.BLACK);
        assertTrue(board.put(1, 0, disk1, true));

        assertEquals(Color.BLACK, board.getColor(1, 0));

        assertEquals(31, board.getHoleDisks(Color.WHITE));
        assertEquals(31, board.getHoleDisks(Color.BLACK));
    }

    @Test
    public void createPositionTest(){
        Position position = new Position();
        assertEquals(-1, position.getRow());
        assertEquals(-1, position.getColumn());

        assertTrue(position.set("A1"));
        assertEquals(0, position.getRow());
        assertEquals(0, position.getColumn());

        position = new Position(1, 1);
        assertEquals(1, position.getRow());
        assertEquals(1, position.getColumn());

        assertTrue(position.set("E8"));
        assertEquals(7, position.getRow());
        assertEquals(4, position.getColumn());

        position = new Position(4, 7);
        assertEquals(4, position.getRow());
        assertEquals(7, position.getColumn());
    }

    @Test
    public void numericWrongPositionTest(){
        Position position = new Position(0, 8);
        assertEquals(0, position.getRow());
        assertEquals(-1, position.getColumn());

        position = new Position(-1, 0);
        assertEquals(-1, position.getRow());
        assertEquals(0, position.getColumn());

        position = new Position(10, 3);
        assertEquals(-1, position.getRow());
        assertEquals(3, position.getColumn());

        position = new Position(0, -2);
        assertEquals(0, position.getRow());
        assertEquals(-1, position.getColumn());

        position = new Position(8, -1);
        assertEquals(-1, position.getRow());
        assertEquals(-1, position.getColumn());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A11", "AAA", "K0", "K9", "111", ",,", ",a,", "", "A", "1"})
    public void bothWrongValueStringTest(String string){
        Position position = new Position();
        assertFalse(position.set(string));
        assertEquals(-1, position.getRow());
        assertEquals(-1, position.getColumn());
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "Q1", ",1", " 1", "O1"})
    public void firstWrongValueStringTest(String string){
        Position position = new Position();
        assertFalse(position.set(string));
        assertEquals(0, position.getRow());
        assertEquals(-1, position.getColumn());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A9", "AA", "A,", "A ", "A0"})
    public void secondWrongValueStringTest(String string){
        Position position = new Position();
        assertFalse(position.set(string));
        assertEquals(-1, position.getRow());
        assertEquals(0, position.getColumn());
    }

    @Test
    public void logicMakeMoveTest(){
        Logic logic = new Logic();
        assertEquals(Color.GREEN, logic.getBoard().getColor(7, 7));

        assertTrue(logic.makeMove(Color.WHITE, 7, 7));
        assertEquals(Color.WHITE, logic.getBoard().getColor(7, 7));

        assertFalse(logic.makeMove(Color.BLACK, 7, 7));
        assertEquals(Color.WHITE, logic.getBoard().getColor(7, 7));
    }

    @Test
    public void logicMakeWrongMoveTest(){
        Logic logic = new Logic();
        assertFalse(logic.makeMove(Color.WHITE, -1, 0));
        assertFalse(logic.makeMove(Color.BLACK, 0, -1));
        assertFalse(logic.makeMove(Color.BLACK, 0, 9));
        assertFalse(logic.makeMove(Color.BLACK, 8, 0));
    }

    @Test
    public void simulateTest1(){
        Simulator simulator = new Simulator();
        Logic logic = new Logic();

        logic.makeMove(Color.WHITE, 0, 0);
        logic.makeMove(Color.BLACK, 1, 0);
        assertEquals(Color.BLACK, logic.getBoard().getColor(1, 0));

        assertEquals(new Position(2, 0), simulator.simulate(Color.WHITE));
        logic.makeMove(Color.WHITE, 2, 0);
        assertEquals(Color.WHITE, logic.getBoard().getColor(1, 0));

        logic.makeMove(Color.BLACK, 2, 1);
        assertEquals(new Position(2, 2), simulator.simulate(Color.WHITE));
    }

    @Test
    public void simulateTest2(){
        Simulator simulator = new Simulator();
        Logic logic = new Logic();

        logic.makeMove(Color.WHITE, 1, 3);
        logic.makeMove(Color.WHITE, 2, 3);
        logic.makeMove(Color.WHITE, 4, 3);
        logic.makeMove(Color.BLACK, 3, 3);

        assertEquals(new Position(0, 3), simulator.simulate(Color.BLACK));
    }

    @Test
    public void spreadTheMoveTest1(){
        Logic logic = new Logic();

        logic.makeMove(Color.BLACK, 2, 6);
        logic.makeMove(Color.WHITE, 3, 5);
        logic.makeMove(Color.WHITE, 5, 4);
        logic.makeMove(Color.WHITE, 6, 4);
        logic.makeMove(Color.BLACK, 7, 4);

        assertTrue(logic.makeMove(Color.BLACK, 4, 4));
        assertEquals(Color.BLACK, logic.getBoard().getColor(3, 5));
        assertEquals(Color.BLACK, logic.getBoard().getColor(5, 4));
        assertEquals(Color.BLACK, logic.getBoard().getColor(6, 4));
    }

    @Test
    public void spreadTheMoveTest2(){
        Logic logic = new Logic();

        logic.makeMove(Color.WHITE, 4, 6);
        logic.makeMove(Color.WHITE, 6, 6);

        assertTrue(logic.makeMove(Color.BLACK, 5, 6));
        assertEquals(Color.WHITE, logic.getBoard().getColor(4, 6));
        assertEquals(Color.WHITE, logic.getBoard().getColor(6, 6));
        assertEquals(Color.BLACK, logic.getBoard().getColor(5, 6));

        assertTrue(logic.makeMove(Color.BLACK, 7, 6));
        assertEquals(Color.BLACK, logic.getBoard().getColor(6, 6));
    }

    @Test
    public void gameEndedTest(){
        Logic logic = new Logic();

        assertFalse(logic.ifEnded());

        logic.makeMove(Color.BLACK, 0, 5);
        logic.makeMove(Color.BLACK, 1, 5);
        logic.makeMove(Color.BLACK, 2, 5);

        assertFalse(logic.ifEnded());
    }
}
