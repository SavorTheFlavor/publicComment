package com.me.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.comment.bean.Action;
import com.me.comment.bean.Group;
import com.me.comment.bean.GroupAction;
import com.me.comment.bean.GroupMenu;
import com.me.comment.bean.Menu;
import com.me.comment.dao.GroupActionDao;
import com.me.comment.dao.GroupDao;
import com.me.comment.dao.GroupMenuDao;
import com.me.comment.dto.ActionDto;
import com.me.comment.dto.GroupDto;
import com.me.comment.dto.MenuDto;
import com.me.comment.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private GroupMenuDao groupMenuDao;

    @Autowired
    private GroupActionDao groupActionDao;

    @Override
    public boolean add(GroupDto groupDto) {

        Group group = new Group();
        BeanUtils.copyProperties(groupDto, group);
        return groupDao.insert(group) == 1;

    }

    @Override
    public boolean remove(Long id) {
        return groupDao.delete(id) == 1;
    }

    @Override
    public boolean modify(GroupDto groupDto) {
        Group group = new Group();
        BeanUtils.copyProperties(groupDto, group);
        return groupDao.update(group) == 1;
    }

    @Override
    public GroupDto getById(Long id) {

        GroupDto groupDto = new GroupDto();
        Group group = groupDao.selectById(id);
        BeanUtils.copyProperties(group, groupDto);
        return groupDto;

    }

    @Override
    public List<GroupDto> getList() {

        List<GroupDto> result = new ArrayList<>();
        List<Group> groupList = groupDao.select(new Group());
        for (Group group : groupList) {
            GroupDto groupDto = new GroupDto();
            result.add(groupDto);
            BeanUtils.copyProperties(group, groupDto);
            groupDto.setpId(0);
        }
        return result;
    }

    @Override
    public GroupDto getMenuList(Long id) {
        GroupDto result = new GroupDto();
        List<MenuDto> menuDtoList = new ArrayList<>();
        result.setMenuDtoList(menuDtoList);
        
        Group group = groupDao.selectMenuListById(id);
        
        BeanUtils.copyProperties(group, result);
        for (Menu menu: group.getMenuList()) {
			MenuDto menuDto = new MenuDto();
			BeanUtils.copyProperties(menu, menuDto);
			menuDtoList.add(menuDto);
		}
        
        
        result.setMenuList(null);
        return result;
    }

    @Override
    @Transactional
    public boolean assignMenu(GroupDto groupDto) {

        //1 清空用户组和菜单、动作的关联关系
        groupMenuDao.deleteByGroupId(groupDto.getId());
        groupActionDao.deleteByGroupId(groupDto.getId());
        //2 新增用户组和菜单的关联关系
        if (groupDto.getMenuIdList() != null && groupDto.getMenuIdList().size() > 0){
            // 准备好批量新增的原料
            List<GroupMenu> list = new ArrayList<>();
            Long groupId = groupDto.getId();
            for(Long menuId : groupDto.getMenuIdList()) {
                if(menuId != null) {
                    GroupMenu groupMenu = new GroupMenu();
                    list.add(groupMenu);
                    groupMenu.setGroupId(groupId);
                    groupMenu.setMenuId(menuId);
                }
            }
            groupMenuDao.insertBatch(list);
        }
        // 保存为用户组分配的动作
        if(groupDto.getActionIdList() != null && groupDto.getActionIdList().size() > 0) {
            List<GroupAction> list = new ArrayList<>();
            for(Long actionId : groupDto.getActionIdList()) {
                if(actionId != null) {
                    GroupAction groupAction = new GroupAction();
                    groupAction.setGroupId(groupDto.getId());
                    groupAction.setActionId(actionId);
                    list.add(groupAction);
                }
            }
            groupActionDao.insertBatch(list);
        }
        return true;
    }

    @Override
    public GroupDto getByIdWithMenuAction(Long id) {
        GroupDto result = new GroupDto();
        List<MenuDto> menuDtoList = new ArrayList<>();
        List<ActionDto> actionDtoList = new ArrayList<>();
        result.setMenuDtoList(menuDtoList);
        result.setActionDtoList(actionDtoList);

        Group group = groupDao.selectMenuListById(id);
        if(group != null) {
            BeanUtils.copyProperties(group, result);
            for(Menu menu : group.getMenuList()) {
                MenuDto menuDto = new MenuDto();
                menuDtoList.add(menuDto);
                BeanUtils.copyProperties(menu, menuDto);
            }

            for(Action action : group.getActionList()) {
                ActionDto actionDto = new ActionDto();
                actionDtoList.add(actionDto);
                BeanUtils.copyProperties(action, actionDto);
            }
        }
        return result;
    }



}