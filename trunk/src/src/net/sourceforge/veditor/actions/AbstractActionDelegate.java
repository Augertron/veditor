//
//  Copyright 2004, KOBAYASHI Tadashi
//  $Id$
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
package net.sourceforge.veditor.actions;

import net.sourceforge.veditor.editor.VerilogEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

abstract public class AbstractActionDelegate implements IEditorActionDelegate,
		IWorkbenchWindowActionDelegate
{
	private VerilogEditor editor;

	public void setActiveEditor(IAction action, IEditorPart targetEditor)
	{
		editor = (VerilogEditor)targetEditor;
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
	
	protected VerilogEditor getEditor()
	{
		return editor;
	}
	protected ISourceViewer getViewer()
	{
		return editor.getViewer();
	}
	protected void beep()
	{
		editor.beep();
	}
}

