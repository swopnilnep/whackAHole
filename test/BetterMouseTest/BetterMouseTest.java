//  Student: Swopnil N. Shrestha
//  Instructor: Dr. Alan Zaring
//  Class: CS-252 2019SP
//  Date: 08/04/2019
//  Purpose: Fix the BetterMouseTest program to make it more intuitive to the user

//
// This example program was adapted from an example program provided with
//
//     Horstmann & Cornell
//     _Core Java, Volume 1 - Fundamentals_
//     Prentice Hall
//

package BetterMouseTest;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;


public class BetterMouseTest
{

    public static void main(String [] commandLineArguments)
    {

        EventQueue.invokeLater(() -> {      // Event dispatching library, holds JComponents
            ProgramFrame frame = new ProgramFrame();
            
            frame.setTitle("Better Mouse Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

        }

    }


class ProgramFrame extends JFrame
{

    public ProgramFrame()
    {

        add(new MouseComponent());
        pack();     // Need to pack items to a frame

        }

    }


class MouseComponent extends JComponent
{

    private static final int OUR_DEFAULT_WIDTH = 600;
    private static final int OUR_DEFAULT_HEIGHT = 400;
    private static final int OUR_SQUARE_SIDE_LENGTH = 40;

    private ArrayList< Rectangle2D > mySquares;     // ArrayList of Rectangle2D elements
    private Rectangle2D myCurrentSquare;            // Rectangle coordinates and dimensions

    private ArrayList< Rectangle2D > squares()
    {

        return mySquares;

        }

    private Rectangle2D currentSquare() // location and size of the current square
    {

        return myCurrentSquare;

        }

    private void setSquares(ArrayList< Rectangle2D > other) //  Make squares into new square ArrayList
    {

        mySquares = other;

        }

    private void setCurrentSquare(Rectangle2D other)    // Set currentSquare to new square
    {

        myCurrentSquare = other;

        }

    public MouseComponent()
    {

        setSquares(new ArrayList< Rectangle2D >());
        setCurrentSquare(null);

        addMouseListener(new MouseHandler());   // Checks for any changes in mouse activity
        addMouseMotionListener(new MouseMotionHandler());   // Checks for any changes in mouse motion

        }

    @Override
    public Dimension getPreferredSize()
    {

        return new Dimension(OUR_DEFAULT_WIDTH, OUR_DEFAULT_HEIGHT);    // Gets the default square dimensions

        }


    @Override
    public void paintComponent(Graphics canvas) // Paints all the squares to the canvas
    {

        for (Rectangle2D squareOnCanvas : squares())
            ((Graphics2D) canvas).draw(squareOnCanvas);

        }

    public Rectangle2D findSquareContainingPoint(Point2D clickPoint)    // Takes in a point object and finds the square relating to it
    {

        int squareNumber;
                                                                        // Solution B
        for (squareNumber = squares().size() - 1 ; squareNumber > -1    // Reverse the for loop to start from the end of squares()
                && ! squares().get(squareNumber).contains(clickPoint);  // This will pick the last-modified or "top-most" element
                -- squareNumber){}

        return squareNumber > -1 ? squares().get(squareNumber) : null;

        }

    public void placeAdditionalSquare(Point2D clickPoint)
    {

        setCurrentSquare(
            new Rectangle2D.Double(
                clickPoint.getX() - OUR_SQUARE_SIDE_LENGTH / 2,
                clickPoint.getY() - OUR_SQUARE_SIDE_LENGTH / 2,
                OUR_SQUARE_SIDE_LENGTH,
                OUR_SQUARE_SIDE_LENGTH
                )
            );

        squares().add(currentSquare());

        repaint();

        System.out.println(
            "New square placed at (" + clickPoint.getX() +", " + clickPoint.getY() + ")"
            );

        }

    public void removeExistingSquare(Rectangle2D existingSquare)
    {

        if (existingSquare != null) {

            if (existingSquare == currentSquare())
                setCurrentSquare(null);

            squares().remove(existingSquare);

            repaint();

            System.out.println("Existing square removed");

            }

        }


    private class MouseHandler extends MouseAdapter
    {

        int myPresses = 0;
        int myClicks = 0;
        boolean newSquareWasPlaced = false;
        Rectangle2D previouslyClickedSquare;

        @Override
        public void mousePressed(MouseEvent event)
        {

            ++ myPresses;
            System.out.println(
                "Mouse press " + myPresses + " at (" + event.getX() + ", " + event.getY() + ")"
                );

            setCurrentSquare(findSquareContainingPoint(event.getPoint()));
            
            if (currentSquare() == null){
                
                placeAdditionalSquare(event.getPoint());                            // Solution A(1), change the cursor once the
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));     // Square has been created
                
                newSquareWasPlaced = true;
                
                } 
            
            }

        @Override
        public void mouseClicked(MouseEvent event)
        {

            ++ myClicks;
            if (event.getClickCount() < 2)
                System.out.println(
                    "Mouse click " + myClicks + " at (" + event.getX() + ", " + event.getY() + ")"
                    );
            else
                System.out.println(
                    "Mouse double-click " + myClicks + " at (" + event.getX() + ", " + event.getY() + ")"
                    );
            
            setCurrentSquare(findSquareContainingPoint(event.getPoint()));
                       
            if (currentSquare() == previouslyClickedSquare && event.getClickCount() >= 2) { 
                
                removeExistingSquare(currentSquare());
                
                if (findSquareContainingPoint(event.getPoint()) == null)            // Solution A(2), Change the cursor back
                    setCursor(Cursor.getDefaultCursor());                           // If the squares are not overlapping
                
            }
            
            
            previouslyClickedSquare = newSquareWasPlaced ? null : currentSquare();
            newSquareWasPlaced = false;
            
            
            }

        }


    private class MouseMotionHandler implements MouseMotionListener
    {

        int myMoves = 0;
        int myDrags = 0;

        @Override
        public void mouseMoved(MouseEvent event)
        {

            ++ myMoves;
            System.out.println(
                "Mouse move " + myMoves + " to (" + event.getX() + ", " + event.getY() + ")"
                );
            

            // Mouse cursor settings
            if (findSquareContainingPoint(event.getPoint()) == null)
                setCursor(Cursor.getDefaultCursor());
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

            }

        @Override
        public void mouseDragged(MouseEvent event)
        {

            ++ myDrags;
            System.out.println(
                "Mouse drag " + myDrags + " to (" + event.getX() + ", " + event.getY() + ")"
                );

            if (currentSquare() != null) {

                currentSquare().setFrame(
                    event.getX() - OUR_SQUARE_SIDE_LENGTH / 2,
                    event.getY() - OUR_SQUARE_SIDE_LENGTH / 2,
                    OUR_SQUARE_SIDE_LENGTH,
                    OUR_SQUARE_SIDE_LENGTH
                    );

                // Remove and put append it to the arrayList
                setCurrentSquare(findSquareContainingPoint(event.getPoint()));
                removeExistingSquare(currentSquare());
                placeAdditionalSquare(event.getPoint());   
                
                repaint();

                }

            }

        }

    }
