/*
 * FocusJTextField.java
 *
 * Created on 12. April 2005, 05:08
 */

package org.jirc.gui;

import java.awt.KeyboardFocusManager;
import javax.swing.JTextField;
import java.util.Collections;
/**
 *
 * @author Taggy
 */
 class FocusJTextField extends JTextField
  { public FocusJTextField()
    { super();
      setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,Collections.EMPTY_SET); 
      setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,Collections.EMPTY_SET); 
    }
  }
