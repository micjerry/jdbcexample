package com.zhihuixueai.sysmgr.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhihuixueai.sysmgr.cache.api.CommonCache;
import com.zhihuixueai.sysmgr.db.dao.api.CommonDao;
import com.zhihuixueai.sysmgr.db.model.LongId;
import com.zhihuixueai.sysmgr.db.model.StringId;

@Component
public class CommonCacheImpl implements CommonCache {
	
	@Autowired
	private CommonDao commonDao;
	
	private ConcurrentHashMap<String, Long> schoolCache = new ConcurrentHashMap<String, Long>();
	
	private ConcurrentHashMap<String, List<String>> roleCache = new ConcurrentHashMap<String, List<String>>();
	
	private ConcurrentHashMap<String, Long> teacherCache = new ConcurrentHashMap<String, Long>();
	
	private ConcurrentHashMap<Long, List<Long>> manageClassCache = new ConcurrentHashMap<Long, List<Long>>();
	
	private ConcurrentHashMap<Long, List<Long>> teachingClassCache = new ConcurrentHashMap<Long, List<Long>>();
	
	private ConcurrentHashMap<Long, Long> studentClassCache = new ConcurrentHashMap<Long, Long>();
	
	private static final boolean USING_CACHE = false;

	@Override
	public long getSchooByUser(String userid) {
		
		Long school_id = schoolCache.get(userid);
		if (school_id == null) {
			school_id = commonDao.getSchoolByUser(userid);
			if (school_id != 0) {
				schoolCache.put(userid, school_id);
			}
		}
		
		return school_id;
	}
	
	@Override
	public List<String> getRolesByUser(String userid) {
		List<String> roles = roleCache.get(userid);
		if (roles == null || roles.isEmpty()) {
			roles = new ArrayList<String>();
			List<StringId> db_roles = commonDao.getRolesByUser(userid);
			if (db_roles == null) {
				return null;
			}
			
			for (StringId db_role: db_roles) {
				roles.add(db_role.getId());
			}
			
			if (USING_CACHE) {
				roleCache.put(userid, roles);
			}
		}
		
		return roles;
	}
	
	@Override
	public long getTeacherByUser(String userid) {
		Long teacher_id = teacherCache.get(userid);
		if (teacher_id == null) {
			teacher_id = commonDao.getTeacherByUser(userid);
			if (teacher_id != 0) {
				teacherCache.put(userid, teacher_id);
			}
		}
		
		return teacher_id;
	}
	
	@Override
    public List<Long> getMangeClassByTeacher(long schoolid, long teacherid) {
		List<Long> reults = manageClassCache.get(teacherid);
		if (reults == null || reults.isEmpty()) {
			reults = new ArrayList<Long>();
			List<LongId> db_reults = commonDao.getMangeClassByTeacher(schoolid, teacherid);
			if (db_reults == null) {
				return null;
			}
			
			for (LongId db_id : db_reults) {
				reults.add(db_id.getId());
			}
			
			if (USING_CACHE) {
				manageClassCache.put(teacherid, reults);
			}
		}
		
		return reults;
	}
	
	public List<Long> getTeachingClassByTeacher(long teacherid) {
		List<Long> reults = teachingClassCache.get(teacherid);
		if (reults == null || reults.isEmpty()) {
			reults = new ArrayList<Long>();
			List<LongId> db_reults = commonDao.getTeachingClassByTeacher(teacherid);
			if (db_reults == null) {
				return null;
			}
			
			for (LongId db_id : db_reults) {
				reults.add(db_id.getId());
			}
			
			if (USING_CACHE) {
				teachingClassCache.put(teacherid, reults);
			}
		}
		return reults;	
	}

	@Override
	public void clearSchoolByUser(String userid) {
		schoolCache.remove(userid);
	}

	@Override
	public void clearRolesByUser(String userid) {
		roleCache.remove(userid);
	}

	@Override
	public void clearTeacherByUser(String userid) {
		teacherCache.remove(userid);
	}

	@Override
	public void clearManageClassByTeacher(long teacherid) {
		manageClassCache.remove(teacherid);
	}

	@Override
	public void clearTeachingClassByTeacher(long teacherid) {
		teachingClassCache.remove(teacherid);
	}
	
	@Override
	public long getClassidByStudent(long studentid) {
		Long classid = studentClassCache.get(studentid);
		if (classid == null) {
			classid = commonDao.getClassidByStudent(studentid);
			if (classid != 0) {
				if (USING_CACHE) {
					studentClassCache.put(studentid, classid);
				}
			}
		}
		
		return classid;
	}
	
	@Override
	public void clearClassidByStudent(long studentid) {
		studentClassCache.remove(studentid);
	}
}
