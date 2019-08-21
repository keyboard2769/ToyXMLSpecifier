/*
 * Copyright (C) 2019 N216
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pppmodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author N216
 */
public class McTableAdaptor implements TableModel  {
  
  static protected final String C_DEFAULT = "...";
  protected int cmListenerCounter = 0;
  
  public int ccGetListenerCounter()
  {return cmListenerCounter;}
  
  @Override public int getRowCount()
  {return 2;}//+++
  
  @Override public int getColumnCount()
  {return 2;}//+++
  
  @Override public String getColumnName(int pxColumnIndex)
  {return C_DEFAULT;}//+++
  
  @Override public Class<?> getColumnClass(int pxColumnIndex)
  {return C_DEFAULT.getClass();}//+++
  
  @Override public boolean isCellEditable(int pxRowIndex, int pxColumnIndex)
  {return false;}//+++
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex)
  {return C_DEFAULT;}//+++
  
  @Override public
  void setValueAt(Object pxaValue, int pxRowIndex, int pxColumnIndex)
  {}//+++
    
  @Override public void addTableModelListener(TableModelListener pxL)
  {cmListenerCounter++;}//+++
    
  @Override public void removeTableModelListener(TableModelListener pxL)
  {cmListenerCounter--;}//+++
    
}//***eof
