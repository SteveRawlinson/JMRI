package jmri.jmrix.loconet.locormi;

import java.lang.Thread;
import jmri.jmrix.loconet.LocoNetMessage;

/**
 * @author Alex Shepherd Copyright (c) 2002
 * @version $Revision: 1.4 $
 */
class LnMessageClientPollThread extends Thread{
    LnMessageClient parent = null ;
   	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LnMessageClientPollThread.class.getName());

    LnMessageClientPollThread( LnMessageClient lnParent ){
        parent = lnParent ;
        this.setDaemon( true );
        this.start();
    }

    public void run() {
        try{
            Object[] lnMessages = null ;
            while( !Thread.interrupted() ){
                lnMessages = parent.lnMessageBuffer.getMessages( parent.pollTimeout ) ;

                if( lnMessages != null ){

                    log.debug( "Recieved Message Array Size: " + lnMessages.length );
                    for( int lnMessageIndex = 0; lnMessageIndex < lnMessages.length; lnMessageIndex++ ){
                        LocoNetMessage message = (LocoNetMessage)lnMessages[ lnMessageIndex ] ;
                        parent.message( message );
                    }
                }
            }
        }
        catch( Exception ex ){
            log.warn( "Exception: " + ex ) ;
        }
    }
}