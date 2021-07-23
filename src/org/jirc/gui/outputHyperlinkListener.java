/*
 * outputHyperlinkListener.java
 *
 * Created on 2. April 2005, 04:59
 */

package org.jirc.gui;

import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import java.util.StringTokenizer;



/**
 *
 * @author Taggy
 */
class outputHyperlinkListener implements HyperlinkListener
{
    public void hyperlinkUpdate(HyperlinkEvent e) {
      if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
        StringTokenizer st = new StringTokenizer(e.getDescription(), " ");
        if (st.hasMoreTokens()) {
          String s = st.nextToken();
          System.err.println("token: " + s);
        }
      }
    }
}
