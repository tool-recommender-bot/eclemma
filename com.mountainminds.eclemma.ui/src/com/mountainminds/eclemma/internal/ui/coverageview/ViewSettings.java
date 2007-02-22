/*******************************************************************************
 * Copyright (c) 2006, 2007 Mountainminds GmbH & Co. KG
 * This software is provided under the terms of the Eclipse Public License v1.0
 * See http://www.eclipse.org/legal/epl-v10.html.
 *
 * $Id$
 ******************************************************************************/
package com.mountainminds.eclemma.internal.ui.coverageview;

import org.eclipse.ui.IMemento;

import com.mountainminds.eclemma.core.analysis.ICounter;
import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;
import com.mountainminds.eclemma.internal.ui.UIMessages;

/**
 * All setting for the coverage view that will become persisted in the view's
 * memento.
 *  
 * @author  Marc R. Hoffmann
 * @version $Revision$
 */
public class ViewSettings {
  
  public static final int ENTRYMODE_PROJECTS      = 1; 
  public static final int ENTRYMODE_PACKAGEROOTS  = 2; 
  public static final int ENTRYMODE_PACKAGES      = 3; 
  public static final int ENTRYMODE_TYPES         = 4; 
  
  private static final String KEY_SORTCOLUMN      = "sortcolumn"; //$NON-NLS-1$
  private static final String KEY_REVERSESORT     = "reversesort"; //$NON-NLS-1$
  private static final String KEY_COUNTERMODE     = "countermode"; //$NON-NLS-1$
  private static final String KEY_HIDEUNUSEDTYPES = "hideunusedtypes"; //$NON-NLS-1$
  private static final String KEY_ENTRYMODE       = "entrymode"; //$NON-NLS-1$
  private static final String KEY_COLUMN0         = "column0"; //$NON-NLS-1$
  private static final String KEY_COLUMN1         = "column1"; //$NON-NLS-1$
  private static final String KEY_COLUMN2         = "column2"; //$NON-NLS-1$
  private static final String KEY_COLUMN3         = "column3"; //$NON-NLS-1$
  
  public interface ICounterMode {
    public int getIdx();
    public String[] getColumnHeaders();
    public String getActionLabel();
    public ICounter getCounter(IJavaElementCoverage coverage);
  }
  
  public static final ICounterMode[] COUNTERMODES = new ICounterMode[] {
    new ICounterMode() {
      public int getIdx() { 
        return 0;
      }
      public String getActionLabel() {
        return UIMessages.SessionsViewCounterModeInstructionsAction_label;
      }
      public ICounter getCounter(IJavaElementCoverage coverage) { 
        return coverage.getInstructionCounter(); 
      }
      public String[] getColumnHeaders() {
        return new String[] {
            UIMessages.SessionsViewColumnElement_label,
            UIMessages.SessionsViewColumnCoverage_label,
            UIMessages.SessionsViewColumnCoveredInstructions_label,
            UIMessages.SessionsViewColumnTotalInstructions_label
        };
      }
    },
    new ICounterMode() {
      public int getIdx() { 
        return 1;
      }
      public String getActionLabel() {
        return UIMessages.SessionsViewCounterModeBlocksAction_label;
      }
      public ICounter getCounter(IJavaElementCoverage coverage) {
        return coverage.getBlockCounter();
      }
      public String[] getColumnHeaders() {
        return new String[] {
            UIMessages.SessionsViewColumnElement_label,
            UIMessages.SessionsViewColumnCoverage_label,
            UIMessages.SessionsViewColumnCoveredBlocks_label,
            UIMessages.SessionsViewColumnTotalBlocks_label
        };
      }
    },
    new ICounterMode() {
      public int getIdx() {
        return 2;
      }
      public String getActionLabel() {
        return UIMessages.SessionsViewCounterModeLinesAction_label;
      }
      public ICounter getCounter(IJavaElementCoverage coverage) {
        return coverage.getLineCounter();
      }
      public String[] getColumnHeaders() {
        return new String[] {
            UIMessages.SessionsViewColumnElement_label,
            UIMessages.SessionsViewColumnCoverage_label,
            UIMessages.SessionsViewColumnCoveredLines_label,
            UIMessages.SessionsViewColumnTotalLines_label
        };
      }
    },
    new ICounterMode() {
      public int getIdx() {
        return 3;
      }
      public String getActionLabel() {
        return UIMessages.SessionsViewCounterModeMethodsAction_label;
      }
      public ICounter getCounter(IJavaElementCoverage coverage) {
        return coverage.getMethodCounter();
      }
      public String[] getColumnHeaders() {
        return new String[] {
            UIMessages.SessionsViewColumnElement_label,
            UIMessages.SessionsViewColumnCoverage_label,
            UIMessages.SessionsViewColumnCoveredMethods_label,
            UIMessages.SessionsViewColumnTotalMethods_label
        };
      }
    },
    new ICounterMode() {
      public int getIdx() {
        return 4;
      }
      public String getActionLabel() {
        return UIMessages.SessionsViewCounterModeTypesAction_label;
      }
      public ICounter getCounter(IJavaElementCoverage coverage) {
        return coverage.getTypeCounter();
      }
      public String[] getColumnHeaders() {
        return new String[] {
            UIMessages.SessionsViewColumnElement_label,
            UIMessages.SessionsViewColumnCoverage_label,
            UIMessages.SessionsViewColumnCoveredTypes_label,
            UIMessages.SessionsViewColumnTotalTypes_label
        };
      }
    }
  };
  
  private static final int[] DEFAULT_COLUMNWIDTH = new int[] {
    300, 80, 120, 120
  };
  
  private int sortcolumn;
  private boolean reversesort;
  private int countermode;
  private int entrymode;
  private boolean hideunusedtypes;
  private int[] columnwidths = new int[4];
  
  public int getSortColumn() {
    return sortcolumn;
  }
  
  public boolean isReverseSort() {
    return reversesort;
  }
  
  public void toggleSortColumn(int column) {
    if (sortcolumn == column) {
      reversesort = !reversesort;
    } else {
      reversesort = false;
      sortcolumn = column;
    }
  }

  public ICounterMode getCounterMode() {
    return COUNTERMODES[countermode];
  }
  
  public void setCounterMode(int idx) {
    countermode = idx;
  }
  
  public int getEntryMode() {
    return entrymode;
  }

  public void setEntryMode(int mode) {
    entrymode = mode;
  }
  
  public boolean getHideUnusedTypes() {
    return hideunusedtypes;    
  }
  
  public void setHideUnusedTypes(boolean flag) {
    hideunusedtypes = flag;
  }
  
  public int[] getColumnWidths() {
    return columnwidths;
  }
  
  public void init(IMemento memento) {
    sortcolumn = getInt(memento, KEY_SORTCOLUMN, 0);
    reversesort = getInt(memento, KEY_REVERSESORT, 0) != 0;
    countermode = getInt(memento, KEY_COUNTERMODE, 0);
    entrymode = getInt(memento, KEY_ENTRYMODE, ENTRYMODE_PROJECTS);
    hideunusedtypes = getInt(memento, KEY_HIDEUNUSEDTYPES, 0) != 0;
    columnwidths[0] = getInt(memento, KEY_COLUMN0, DEFAULT_COLUMNWIDTH[0]);
    columnwidths[1] = getInt(memento, KEY_COLUMN1, DEFAULT_COLUMNWIDTH[1]);
    columnwidths[2] = getInt(memento, KEY_COLUMN2, DEFAULT_COLUMNWIDTH[2]);
    columnwidths[3] = getInt(memento, KEY_COLUMN3, DEFAULT_COLUMNWIDTH[3]);
  }
  
  public void save(IMemento memento) {
    memento.putInteger(KEY_SORTCOLUMN, sortcolumn);
    memento.putInteger(KEY_REVERSESORT, reversesort ? 1 : 0);
    memento.putInteger(KEY_COUNTERMODE, countermode);
    memento.putInteger(KEY_ENTRYMODE, entrymode);
    memento.putInteger(KEY_HIDEUNUSEDTYPES, hideunusedtypes ? 1 : 0);
    memento.putInteger(KEY_COLUMN0, columnwidths[0]);
    memento.putInteger(KEY_COLUMN1, columnwidths[1]);
    memento.putInteger(KEY_COLUMN2, columnwidths[2]);
    memento.putInteger(KEY_COLUMN3, columnwidths[3]);
  }
  
  private int getInt(IMemento memento, String key, int preset) {
    if (memento == null) {
      return preset;
    } else {
      Integer i = memento.getInteger(key);
      return i == null ? preset : i.intValue();
    }
  }

}
