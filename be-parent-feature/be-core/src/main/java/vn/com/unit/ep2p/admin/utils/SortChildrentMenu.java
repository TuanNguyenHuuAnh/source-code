package vn.com.unit.ep2p.admin.utils;

import java.util.Comparator;

import vn.com.unit.ep2p.admin.dto.MenuDto;

public class SortChildrentMenu implements Comparator<MenuDto>
{

    @Override
    public int compare(MenuDto menuA, MenuDto menuB) {
        return menuA.getSort() - menuB.getSort();
    }
    
}