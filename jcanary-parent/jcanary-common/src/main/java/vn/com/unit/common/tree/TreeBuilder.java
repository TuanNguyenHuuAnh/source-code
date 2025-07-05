/*******************************************************************************
 * Class        ：TreeBuilder
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.common.tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lombok.NoArgsConstructor;
import vn.com.unit.common.constant.CommonConstant;

/**
 * TreeBuilder
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@NoArgsConstructor
public class TreeBuilder<T extends TreeNode> {

	private static final String STATE_OPEN = "open";
	
	private static final String STATE_SELECTED = "selected";
	
	private List<T> objectList;
	
	public TreeBuilder(List<T> objectList) {
		if (objectList == null) {
			this.objectList = new ArrayList<>();
		} else {
			this.objectList = objectList;
		}
	}
	
	public List<TreeObject<T>> buildTree() {
        HashMap<Long, TreeObject<T>> objectTable = new HashMap<>();

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
                        subNode = new TreeObject<>(objectSub);
                        objectTable.put(objectSubId, subNode);
                    }
                    
                    objectDTOList.add(subNode);
                }
            }

            TreeObject<T> node = objectTable.get(objectId);
            if (node == null) {
                node = new TreeObject<>(object);
            }
            node.setChildren(objectDTOList);
            objectTable.remove(objectId);
            objectTable.put(objectId, node);
        }

        List<TreeObject<T>> objectRootList = new ArrayList<>();
        for (T objectTmp : objectList) {
            if (objectTmp.getNodeParentId() == CommonConstant.NUMBER_ZERO_L) {
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
					
					if( objectSub.getNodeId().equals(object.getNodeParentId()) || findOut ) {
						
						if( findOut ) {
							if( objectSub.getNodeParentId().equals(object.getNodeParentId()) ) {
								if(object.getNodeOrder() != null && object.getNodeOrder() < objectSub.getNodeOrder() ) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<JSTree> buildJSTree() {
		HashMap<Long, JSTree> objectTable = new HashMap<>();

		List<JSTree> objectRootList = new ArrayList<>();

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

			if (!objectDTOList.isEmpty()) {
				node.addState(STATE_SELECTED, false);
			}
			node.setChildren(objectDTOList);
			objectTable.put(objectId, node);
		}

		for (T objectTmp : objectList) {
			if (objectTmp.getNodeParentId() == CommonConstant.NUMBER_ZERO_L) {
				JSTree root = objectTable.get(objectTmp.getNodeId());
				objectRootList.add(root);
			}
		}

		return objectRootList;
	}
	
	public List<T> getParentBySub(T sub) {
		List<T> result = new ArrayList<>();
		Long subParentId = sub.getNodeParentId();
		Long rootId = CommonConstant.NUMBER_ZERO_L;
		T nodeRoot = objectList.stream().filter(node -> node.getNodeParentId().equals(rootId)).findAny().orElse(null);
		Long root = nodeRoot != null ? nodeRoot.getNodeId() : 1L;

		while (!subParentId.equals(root)) {
			Long parentId = subParentId;
			T nodeParent = objectList.stream().filter(parent -> parent.getNodeId().equals(parentId)).findAny()
					.orElse(null);
			if (null != nodeParent) {
				result.add(nodeParent);
				// set node to check condition
				subParentId = nodeParent.getNodeParentId();
			} else {
				break;
			}
		}

		return result;
	}
}
