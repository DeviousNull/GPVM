package taiga.gpvm.registry;

import taiga.code.io.DataNode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * Indicates that a class can be used to store rendering data for a specific
 * {@link Renderer}.  The implementing class must have a constructor that takes
 * in a {@link DataNode} as an argument.  This {@link DataNode} will contain the
 * value of the rendering-info field for a {@link RenderingEntry}, or null if
 * no data is provided for the a given {@link RenderingEntry}
 * 
 * @author russell
 */
public class RenderingInfo {
}
