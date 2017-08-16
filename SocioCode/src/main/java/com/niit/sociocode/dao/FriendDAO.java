package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.Friend;

public interface FriendDAO {

	// Select * from Friend where userId = ? and Status = 'A'
	public List<Friend> getMyFriends(String userId);

	public Friend get(String userId, String friendId);

	public boolean save(Friend friend);

	public boolean update(Friend friend);

	public boolean delete(String userId, String friendId);

	// Select * from Friend where friendId = ? and Status = 'N'
	public List<Friend> getNewFriendRequests(String userId);

	public void setOnline(String friendId);

	public void setOffline(String friendId);

	// Select * from Friend where userId = ? and Status = 'N'

	public List<Friend> getRequestsSendByMe(String loggedInUserId);

}
