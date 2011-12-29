package controller;

import model.*;
import view.*;

/* Command line controller class in which the
 * game will essentially run on.
 * Class sets motion for the game
 */

public class CmdController{
    private Game game;
    private Computer computer;
    private CommandLine cmdLine;
    private BoardGame boardGame;
    private int rows;
    private int cols;
    private String dimensions;
    private String cmdEntd;
    private String exit;
    private String readyToStart;
    private String playerName;
    private String typeBoardSize;
        private String[] commands = {"start","quit", "save", "pause", "resume", "load", "roll", "move", "man"};
        private String sizeType;
        private int rolled;
        private boolean hasRolled; //when a die has not been rolled, variable is false. when rolled variable is true
        private boolean started;
        
    public CmdController(Game game, CommandLine cmdLine){
            this.game = game;
            this.cmdLine = cmdLine;
            hasRolled = false;
            started = false;
        }
    
    public void getInputs(){
            playerName = cmdLine.getName();
            exit = cmdLine.getExit();
            dimensions =  cmdLine.getDimensions();
            //readyToStart = cmdLine.getReady();
            typeBoardSize = cmdLine.getSizeType();
            cmdEntd = cmdLine.getCommand();
            sizeType = cmdLine.getSizeType();
    }
    
    public void calcDimensions(){
            String[] results = new String[2];
            results = dimensions.split("X");
            rows = Integer.parseInt(results[0]);
            cols = Integer.parseInt(results[1]);
                                
//            System.out.println(rows);
//            System.out.println(cols);
    }

        //this is where the command from the user is given to the Game class!!
    
    public String getSizeType(){
            return sizeType;
        }
        
        public String getCmdEntered(){
            return cmdEntd;
        }

    public void cmdHandler(String cmd){
        String errMessage = "";
        String instruction = cmd.trim();
        String[]results = instruction.split(" ");
        
         if(results[0].equalsIgnoreCase(commands[0])&&started==false){

             game.start(cmdLine.getName(), rows, cols);
             started = true; //notifies that the game has started so certain commands can only be executed after game started
         }
        else if(results[0].equalsIgnoreCase(commands[1])&&started==true){
             game.quit();

             cmdLine.startGame();
             game.start(cmdLine.getName(), rows, cols);
    }
        

        //else if(results[0].equalsIgnoreCase(commands[2]))
            // game.save();
        else if(results[0].equalsIgnoreCase(commands[3])&&started==true)
             game.pause();
         
        else if(results[0].equalsIgnoreCase(commands[4]))
             game.resume();
        
//        else if(results[0].equalsIgnoreCase(commands[5])){
//           try{
//                if(results[1] != null)
//                //game.load(results[1]);
//           }
//           catch(Exception e){
//                errMessage = "No filename specified. To use load command type 'load filename'. For more info type man load";
//                cmdLine.cmdError(errMessage);
//           }       
      //  }
        
        else if(results[0].equalsIgnoreCase(commands[6]) && hasRolled==false&&started==true){
             game.roll();
             
             cmdLine.printRolled(game.getNumberRolled());
             hasRolled=true; //move can only be executed after die is rolled so permission is given to move
        }
             
        else if(results[0].equalsIgnoreCase(commands[7])&&started==true&&hasRolled==true)
        {}  //game.move();
        
        else if(results[0].equalsIgnoreCase(commands[8]))
            try{
                if(results[1] != null)
                    game.manCaller(results[1], commands.length);  
            }catch(Exception e){
                errMessage = "No command specified. To use man command type 'man' and the name of command in query";
                cmdLine.cmdError(errMessage);
            }
        else{
            if(hasRolled ==true&&(results[0].equalsIgnoreCase(commands[6]))){
                errMessage = "You have already rolled. Please move your token.";
                cmdLine.cmdError(errMessage);
            }
            else if(started==false){
                errMessage = "You need to start the game first.";
                cmdLine.cmdError(errMessage);
            }
            else if((hasRolled==false)&&(results[0].equalsIgnoreCase(commands[0]))||(results[0].equalsIgnoreCase(commands[7]))){
                if(results[0].equalsIgnoreCase(commands[0])){
                    errMessage = "You've already pressed start.";
                    cmdLine.cmdError(errMessage);
                }
                else{
                    errMessage = "You need to roll die before you can move.";
                    cmdLine.cmdError(errMessage);
                }
            }
            else
                System.out.println("Unknown command. To see the list of possible commands type in 'man'");
        }
    }
    
    public int getRow(){
        return rows;
    }
    
    public int getCols(){
        return cols;
    }
        
        
}
