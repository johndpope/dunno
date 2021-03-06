package net.overmy.dunno.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import net.overmy.dunno.Core;
import net.overmy.dunno.ashley.AshleyWorld;
import net.overmy.dunno.ashley.system.RenderSystem;
import net.overmy.dunno.logic.level.DynamicLevels;

/*
        Created by Andrey Mikheev on 19.07.2018
        Contact me → http://vk.com/id17317
*/
public final class GameMasterDebug {
    private static Array< Vector3 > pushedPositions = null;
    private static StringBuilder    log             = null;
    private static StringBuilder    logMore         = null;
    private static long startTime;


    private GameMasterDebug () {
    }


    public static void init () {
        log = new StringBuilder();
        logMore = new StringBuilder();
        pushedPositions = new Array< Vector3 >();
    }


    /*
        ==========================================
        Game Master keys:
        ENTER = save 1 player position and angle
        BACKSPACE = clear all positions array
        1 = show array as HoverCoins
        2 = show array as Boxes
        3 = show array as Action Script for NPC
        ==========================================
        */
    public static void workWithKeyboard () {
        final String MARKER = "○";

        if ( Gdx.input.isKeyJustPressed( Input.Keys.ENTER ) ) {
            log.setLength( 0 );
            log.append( MARKER );
            log.append( " Push (" );
            log.append( pushedPositions.size );
            log.append( ") for location " );
            log.append( DynamicLevels.getCurrent() );

            pushedPositions.add( new Vector3( Core.playerPosition ) );

            logMore.setLength( 0 );
            logMore.append( "new Vector3( " );
            logMore.append( Core.playerPosition.x );
            logMore.append( "f, " );
            logMore.append( Core.playerPosition.y );
            logMore.append( "f, " );
            logMore.append( Core.playerPosition.z );
            logMore.append( "f ), ► Angle " );
            logMore.append( Core.playerAngle );
            logMore.append( " ◄" );

            Gdx.app.debug( log.toString(), logMore.toString() );
        }

        if ( Gdx.input.isKeyJustPressed( Input.Keys.BACKSPACE ) ) {
            log.setLength( 0 );
            log.append( MARKER );
            log.append( " " );
            log.append( pushedPositions.size );
            log.append( " pushed positions" );

            logMore.setLength( 0 );
            logMore.append( "cleared " );
            logMore.append( MARKER );

            Gdx.app.debug( log.toString(), logMore.toString() );

            pushedPositions.clear();
        }

        if ( Gdx.input.isKeyJustPressed( Input.Keys.NUM_1 ) ) {
            log.setLength( 0 );
            log.append( "\n" );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " Hover Coins " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );

            logMore.setLength( 0 );
            for ( Vector3 pushedPosition : pushedPositions ) {
                logMore.append( "\nobjects.add( hoverCoin( " );
                logMore.append( pushedPosition.x );
                logMore.append( "f, " );
                logMore.append( pushedPosition.y );
                logMore.append( "f, " );
                logMore.append( pushedPosition.z );
                logMore.append( "f) );" );
            }
            logMore.append( "\n" );

            Gdx.app.debug( log.toString(), logMore.toString() );
        }

        if ( Gdx.input.isKeyJustPressed( Input.Keys.NUM_2 ) ) {
            log.setLength( 0 );
            log.append( "\n" );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " Boxes " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );

            logMore.setLength( 0 );
            logMore.append( "\n" );
            for ( Vector3 pushed : pushedPositions ) {
                logMore.append( "objects.add( box( " );
                logMore.append( pushed.x );
                logMore.append( "f, " );
                logMore.append( pushed.y );
                logMore.append( "f, " );
                logMore.append( pushed.z );
                logMore.append( "f) );\n" );
            }
            logMore.append( "\n" );

            Gdx.app.debug( log.toString(), logMore.toString() );
        }

        if ( Gdx.input.isKeyJustPressed( Input.Keys.NUM_3 ) ) {
            log.setLength( 0 );
            log.append( "\n" );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " Action Script for NPC " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );
            log.append( " " );
            log.append( MARKER );

            logMore.setLength( 0 );
            logMore.append( "\n" );
            logMore.append( "init position = " );
            logMore.append( pushedPositions.first().x );
            logMore.append( "f, " );
            logMore.append( pushedPositions.first().y );
            logMore.append( "f, " );
            logMore.append( pushedPositions.first().z );
            logMore.append( "f" );
            logMore.append( "\n\n" );

            for ( Vector3 pushed : pushedPositions ) {
                logMore.append( "queue.add( wait( " );
                logMore.append( MathUtils.random( 0.3f, 2.5f ) );
                logMore.append( "f ) );\n" );
                if ( MathUtils.randomBoolean() ) {
                    logMore.append( "queue.add( hunt() );\n" );
                }
                logMore.append( "queue.add( move(" );
                logMore.append( pushed.x );
                logMore.append( "f, " );
                logMore.append( pushed.z );
                logMore.append( "f) );\n" );
            }
            logMore.append( "\n" );

            Gdx.app.debug( log.toString(), logMore.toString() );
        }
    }


    public static void dispose () {
        pushedPositions.clear();
        pushedPositions = null;

        log = null;
        logMore = null;
    }


    public static void showFPS () {
        if ( TimeUtils.nanoTime() - startTime > 1000000000 ) /* 1,000,000,000ns == one second */ {
            RenderSystem rend = AshleyWorld.getEngine().getSystem( RenderSystem.class );
            int models = rend.getVisibleModelsCount();
            int totalModels = rend.getTotalModelsCount();

            log.setLength( 0 );
            log.append( Gdx.graphics.getFramesPerSecond() );
            log.append( " " );
            log.append( " Models=" );
            log.append( models );
            log.append( "/" );
            log.append( totalModels );
/*

                DecalSystem decalSystem = AshleyWorld.getEngine().getSystem( DecalSystem.class );
                log.append( " Decals=" );
                log.append( decalSystem.getVisibleDecalCount() );
                log.append( "/" );
                log.append( decalSystem.getDecalCount() );
*/

            log.append( " ░▒▓" );

            Gdx.app.log( "▓▒░ FPS", log.toString() );
            startTime = TimeUtils.nanoTime();

            //fpsLabel.setText( log.toString() );
        }
    }
}
