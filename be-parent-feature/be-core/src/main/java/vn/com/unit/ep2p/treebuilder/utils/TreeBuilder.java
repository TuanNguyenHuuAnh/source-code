/*******************************************************************************
 * Class        TreeBuilder
 * Created date 2019/02/19
 * Lasted date  2019/02/19
 * Author       KhoaNA
 * Change log   2019/02/19 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.treebuilder.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import vn.com.unit.ep2p.admin.utils.NumberUtil;


/**
 * TreeBuilder
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class TreeBuilder<T extends TreeNode> {
	
	private static final String STATE_OPEN = "open";
	
	private static final String STATE_SELECTED = "selected";
	
	private List<T> objectList;
	
	public TreeBuilder(List<T> objectList) {
		this.objectList = objectList;
	}
	
	public List<TreeObject<T>> buildTree() {
        Hashtable<Long, TreeObject<T>> objectTable = new Hashtable<>();

        for (int i = 0; i < objectList.size(); i++) {
        	T object = objectList.get(i);
        	Long objectId = object.getNodeId();

        	List<TreeObject<T>> objectDTOList = new ArrayList<>();
            for (int j = 0; j < objectList.size(); j++) {
            	
            	T objectSub = objectList.get(j);
                if (objectId.equals(objectSub.getNodeParentId())) {
                	Long objectSubId = objectSub.getNodeId();
                	TreeObject<T> subNode = objectTable.get(objectSubId);
                	
                    if (subNode == null) {
                        subNode = new TreeObject<T>(objectSub);
                        objectTable.put(objectSubId, subNode);
                    }
                    
                    objectDTOList.add(subNode);
                }
            }

            TreeObject<T> node = objectTable.get(objectId);
            if (node == null) {
                node = new TreeObject<T>(object);
            }
            node.setChildren(objectDTOList);
            objectTable.remove(objectId);
            objectTable.put(objectId, node);
        }

        List<TreeObject<T>> objectRootList = new ArrayList<>();
        for (T objectTmp : objectList) {
            if (objectTmp.getNodeParentId() == null) {
            	TreeObject<T> root = objectTable.get(objectTmp.getNodeId());
            	objectRootList.add(root);
            }
        }
        return objectRootList;
    }
	
	public List<T> sortWithTree() {
		LinkedList<T> linkedList = new LinkedList<>();
		
		for (T object : objectList) {
			if( object.getNodeParentId() == null ) {
				linkedList.add(object);
			} else {
				int index = 0;
				boolean findOut = false;
				for(T objectSub : linkedList) {
					
					if( NumberUtil.equals(objectSub.getNodeId(), object.getNodeParentId()) || findOut == true ) {
						
						if( findOut ) {
							if( NumberUtil.equals(objectSub.getNodeParentId(), object.getNodeParentId()) ) {
								if( object.getNodeOrder() < objectSub.getNodeOrder() ) {
									index++;
									break;
								}
							} else {
								break;
							}
						}
						
						findOut = true;
					}

					index++;
				}
				
				linkedList.add(index, object);
			}
		}
		
		return new ArrayList<>(linkedList);
	}
	
	public List<JSTree> buildJSTree() {
        Hashtable<Long, JSTree> objectTable = new Hashtable<>();

        for (int i = 0; i < objectList.size(); i++) {
        	T object = objectList.get(i);
        	Long objectId = object.getNodeId();

        	List<JSTree> objectDTOList = new ArrayList<>();
            for (int j = 0; j < objectList.size(); j++) {
            	
            	T objectSub = objectList.get(j);
                if (objectId.equals(objectSub.getNodeParentId())) {
                	Long objectSubId = objectSub.getNodeId();
                	JSTree subNode = objectTable.get(objectSubId);
                	
                    if (subNode == null) {
                        //subNode = new TreeObject<T>(objectSub);
                    	subNode = new JSTree(objectSub.getNodeId(), objectSub.getNodeName());
                    	subNode.addState(STATE_OPEN, true);
                    	subNode.addState(STATE_SELECTED, objectSub.getSelected());
                        objectTable.put(objectSubId, subNode);
                    }
                    
                    objectDTOList.add(subNode);
                }
            }

            JSTree node = objectTable.get(objectId);
            if (node == null) {
                node = new JSTree(object.getNodeId(), object.getNodeName());
                node.addState(STATE_OPEN, true);
                node.addState(STATE_SELECTED, object.getSelected());
            }
            
            if( !objectDTOList.isEmpty() ) {
            	node.addState(STATE_SELECTED, false);
            }
            node.setChildren(objectDTOList);
            objectTable.put(objectId, node);
        }

        List<JSTree> objectRootList = new ArrayList<>();
        for (T objectTmp : objectList) {
            if (objectTmp.getNodeParentId() == null) {
            	JSTree root = objectTable.get(objectTmp.getNodeId());
            	objectRootList.add(root);
            }
        }
        return objectRootList;
    }
}