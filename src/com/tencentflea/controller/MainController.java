package com.tencentflea.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.Constant;

import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Comment;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.User;
import com.tencentflea.service.api.MainService;
import com.tencentflea.service.api.UserService;

@Controller(value = "mainController")
public class MainController {
	@Resource(name = "mainServiceImpl")
	MainService mainService;
	@Resource(name = "userServiceImpl")
	UserService userService;

	/**
	 * 根据itemID获取Item信息(sell)
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/sellItemDetail")
	public void sellItemDetail(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			long itemID = Long.parseLong(request.getParameter("itemID"));
			ret = mainService.getItemInfoByItemId(itemID);
			ret.put("state", 0);
			ret.put("info", "success");
		} catch (Exception e) {
			ret.put("info", e.getMessage());
			ret.put("state", 1);
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 根据itemID获取Item信息(inquiry)
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/inquiryItemDetail")
	public void inquiryItemDetail(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			long itemID = Long.parseLong(request.getParameter("itemID"));
			ret = mainService.getItemInfoByItemId(itemID);
			ret.put("state", 0);
			ret.put("info", "success");
		} catch (Exception e) {
			ret.put("info", e.getMessage());
			ret.put("state", 1);
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 收藏
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/toggleCollectSellItem")
	public void toggleCollectSellItemAction(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxName = request.getParameter("rtxName");
			long itemId = Long.parseLong(request.getParameter("itemID"));
			Collection collection = new Collection();
			collection.setItemId(itemId);
			collection.setRtxName(rtxName);
			collection.setCollectionDate(new Date());
			int state = mainService.saveCollection(collection);
			if (state == Constant.COLLECT_FAIL) {
				ret.put("state", -1);
				ret.put("info", "fail");
			} else if (state == Constant.COLLECT_SUCCESS) {
				mainService.saveItemMessage(itemId, rtxName, "",
						Constant.COLLECTION_TYPE);
				ret.put("state", 0);
				ret.put("info", "Collect Success!");
			} else {
				mainService.deleteMessage(rtxName, itemId,
						Constant.COLLECTION_TYPE);
				ret.put("state", 1);
				ret.put("info", "Undo Collect Success!");
			}
		} catch (Exception e) {
			ret.put("info", e.getMessage());
			ret.put("state", -1);
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 发布商品信息（卖）
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 * 
	 */
	@RequestMapping(value = "appImage/sellItem")
	public void sellItemAction(
			@RequestParam(value="profile",required=false) CommonsMultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		StringBuffer imageBuffer = new StringBuffer();
		try {
			String rtxName = request.getParameter("rtxName");
			String itemTitle = request.getParameter("itemTitle");
			String description = request.getParameter("description");
			int price = Integer.parseInt(request.getParameter("price"));
			int itemType = Integer.parseInt(request.getParameter("itemType"));
			Item item = new Item();
			item.setCollectionNum(0);
			item.setItemDescription(description);
			item.setItemPrice(price);
			item.setOnShelf(Constant.ON_SHELF);
			item.setRtxName(rtxName);
			item.setItemTitle(itemTitle);
			item.setItemType(itemType);
			item.setReleaseDate(new Date());
			item.setSaleOrBuy(Constant.SALE_ITEM);
			// 获取图片
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null) {
					// 取得当前上传文件的文件名称
					String myFileName = files[i].getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						// 重命名上传后的文件名
						String imageRootDir = "image/";
						Date date = new Date();
						SimpleDateFormat sdf1 = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						String timestamp = sdf1.format(date);
						String imageFileName = timestamp + i + ".jpg";
						System.out.println("The "+i+" image is "+imageFileName);
						String imageDir = imageRootDir + imageFileName;
						String curRealPath = request.getSession()
								.getServletContext().getRealPath("/");
						// 定义上传路径
						String imagePath = curRealPath + imageDir;
						File localFile = new File(imagePath);
						files[i].transferTo(localFile);
						Image img = ImageIO.read(new File(imagePath));
						// 判断图片格式是否正确
						int newWidth;
						int newHeight;
						// 为等比缩放计算输出的图片宽度及高度
						double rate1 = ((double) img.getWidth(null))
								/ (double) 1000 + 0.1;
						double rate2 = ((double) img.getHeight(null))
								/ (double) 1000 + 0.1;
						// 根据缩放比率大的进行缩放控制
						double rate = rate1 > rate2 ? rate1 : rate2;
						newWidth = (int) (((double) img.getWidth(null)) / rate);
						newHeight = (int) (((double) img.getHeight(null)) / rate);
						BufferedImage tag = new BufferedImage((int) newWidth,
								(int) newHeight, BufferedImage.TYPE_INT_RGB);

						/*
						 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高
						 * 生成的图片质量比较好 但速度慢
						 */
						tag.getGraphics().drawImage(
								img.getScaledInstance(newWidth, newHeight,
										Image.SCALE_SMOOTH), 0, 0, null);
						ImageIO.write(tag, "jpg", new File(imagePath));
						imageBuffer.append(timestamp+i);
						if (i < files.length - 1) {
							imageBuffer.append("*");
						}
						System.out.println(imageBuffer.toString());
					}
				}
			}
			item.setImageUrl(imageBuffer.toString());
			mainService.saveItem(item);
			ret.put("state", 0);
			ret.put("info", "success");
		} catch (Exception e) {
			ret.put("info", e.getMessage());
			ret.put("state", 1);
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 修改item
	 * 
	 * @param files
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 * 
	 */
	@RequestMapping(value = "appImage/editItemInfo")
	public void editItemInfoAction(
			@RequestParam(value = "profile", required = false) CommonsMultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		StringBuffer imageBuffer = new StringBuffer();
		try {
			// (rtxName, itemTitle, picture1, optional picture2, …, optional
			// picture6, itemType, description, price, location)

			String rtxName = request.getParameter("rtxName");
			String itemTitle = request.getParameter("itemTitle");
			String description = request.getParameter("description");
			int price = Integer.parseInt(request.getParameter("price"));
			int itemType = Integer.parseInt(request.getParameter("itemType"));
			JSONArray tempJsonArray = new JSONArray(
					request.getParameter("picToDelete"));
			Item item = new Item();
			item.setCollectionNum(0);
			item.setItemDescription(description);
			item.setItemPrice(price);
			item.setOnShelf(Constant.ON_SHELF);
			item.setRtxName(rtxName);
			item.setItemTitle(itemTitle);
			item.setItemType(itemType);
			item.setReleaseDate(new Date());
			item.setSaleOrBuy(Constant.SALE_ITEM);
			item.setImageUrl("");
			if (files != null) {
				// 获取图片
				for (int i = 0; i < files.length; i++) {
					if (files[i] != null) {
						// 取得当前上传文件的文件名称
						String myFileName = files[i].getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (myFileName.trim() != "") {
							// 重命名上传后的文件名
							String imageRootDir = "image/";
							Date date = new Date();
							String imageFileName = null;
							if (i < tempJsonArray.length()) {
								String oldName = ((JSONObject) tempJsonArray
										.get(i)).getString("imageUrl");
								imageFileName = oldName + ".jpg";
							} else {
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyyMMddHHmmss");
								String timestamp = sdf.format(date);
								imageFileName = timestamp + ".jpg";
							}
							String imageDir = imageRootDir + imageFileName;
							String curRealPath = request.getSession()
									.getServletContext().getRealPath("/");
							// 定义上传路径
							String imagePath = curRealPath + imageDir;
							System.out.println(imagePath);
							File localFile = new File(imagePath);
							files[i].transferTo(localFile);
							Image img = ImageIO.read(new File(imagePath));
							// 判断图片格式是否正确
							int newWidth;
							int newHeight;
							// 为等比缩放计算输出的图片宽度及高度
							double rate1 = ((double) img.getWidth(null))
									/ (double) 1000 + 0.1;
							double rate2 = ((double) img.getHeight(null))
									/ (double) 1000 + 0.1;
							// 根据缩放比率大的进行缩放控制
							double rate = rate1 > rate2 ? rate1 : rate2;
							newWidth = (int) (((double) img.getWidth(null)) / rate);
							newHeight = (int) (((double) img.getHeight(null)) / rate);
							BufferedImage tag = new BufferedImage(
									(int) newWidth, (int) newHeight,
									BufferedImage.TYPE_INT_RGB);

							/*
							 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高
							 * 生成的图片质量比较好 但速度慢
							 */
							tag.getGraphics().drawImage(
									img.getScaledInstance(newWidth, newHeight,
											Image.SCALE_SMOOTH), 0, 0, null);
							ImageIO.write(tag, "jpg", new File(imagePath));
							imageBuffer.append(imagePath);
							if (i < files.length - 1) {
								imageBuffer.append("*");
							}
						}
					}
				}
			}
			item.setImageUrl(imageBuffer.toString());
			mainService.updateItem(item);
			ret.put("state", 1);
			ret.put("info", "fail");
		} catch (Exception e) {
			ret.put("info", e.getMessage());
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 评论
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @throws JSONException
	 *             SDON异常
	 * @throws IOException
	 *             IO异常
	 */
	@RequestMapping(value = "app/commentItem")
	// 请求分发
	public void commentAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException { // 设置字符编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();

		try {
			HttpSession session = request.getSession(true);
			User uesr = (User)session.getAttribute("user");
			// 获取输入参数
			long itemID = Long.parseLong(request.getParameter("itemID"));
			String rtxName = request.getParameter("rtxName");
			String content = request.getParameter("content");
			Comment comment = new Comment();

			// 设置comment实例相关的参数
			comment.setItemId(itemID);

			comment.setRtxName(rtxName);
			comment.setCommentContent(content);
			comment.setCommentDate(new Date());
			if (uesr != null) {
				comment.setIcon(uesr.getIcon());
			} else {
				comment.setIcon("default");
			}
			// 新建Item并存储
			int result = mainService.saveCommentItem(comment)
					& mainService.saveItemMessage(itemID, rtxName, content,
							Constant.COMMENT_TYPE);
			if (result == Constant.SUCCESS) {
				ret.put("state", 0);
				ret.put("info", "success");
			} else {
				ret.put("state", 1);
				ret.put("info", "评论失败");
			}
		} catch (Exception e)// 捕捉异常
		{
			ret.put("exception", "appear an exception");
			ret.put("info", e.getMessage());// 将异常的内容写入到“info”中
		}
		response.getWriter().write(ret.toString());// 将处理后的结果写入到response中
	}

	@RequestMapping(value = "app/inquiryItem")
	// 请求分发
	public void inquiryItemAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			// 获取用户参数
			String rtxName = request.getParameter("rtxName");
			User user = userService.getUserByRTX(rtxName);
			String itemTitle = request.getParameter("itemTitle");
			String description = request.getParameter("description");
			int itemType = Integer.parseInt(request.getParameter("itemType"));
			// 创建用户购买请求并将其存储
			int result = mainService.saveInquiryItem(user, itemTitle, itemType,
					description);
			if (result == Constant.SUCCESS) {
				ret.put("state", 0);
				ret.put("info", "success");
			} else {
				ret.put("state", 1);
				ret.put("info", "发布购买失败");
			}
		} catch (Exception e)// 捕捉异常
		{
			ret.put("state", e.getMessage());// 将异常的内容写入到“info”中
			ret.put("info", "异常：发布购买失败啦");
		}
		response.getWriter().write(ret.toString());// 将处理后的结果写入到response中
	}

	@RequestMapping(value = "app/toggleSellItemOnShelf ")
	// 请求分发
	public void toggleSellItemOnShelfAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		// 设置字符集
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			// 获取用户输入参数
			int itemID = Integer.parseInt(request.getParameter("itemID"));
			// 将物品上架/下架信息更新
			int result = mainService.saveOnShelfState(itemID);
			if (result == Constant.ON_SHELF) {
				ret.put("state", 0);
				ret.put("info", "on shelf success");
			} else if (result == Constant.OFF_SHELF) {
				ret.put("state", 1);
				ret.put("info", "off shelf suncess");
			} else {
				ret.put("state", -1);
				ret.put("info", "failed");
			}
		} catch (Exception e) {
			ret.put("info", e.getMessage());// 将异常的内容写入到“info”中
		}
		response.getWriter().write(ret.toString());// 将处理后的结果写入到response中
	}

	@RequestMapping(value = "appImage/editAccountInfo")
	// 请求分发
	public void editAccountAction(
			@RequestParam(value = "profile", required = false) CommonsMultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, JSONException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxName = request.getParameter("rtxName");
			User oldUser = userService.getUserByRTX(rtxName);
			User user = new User();
			user.setRtxName(rtxName);
			user.setId(oldUser.getId());
			String locationString = request.getParameter("location");
			int location = -1;
			if (locationString != null) {
				location = Integer.parseInt(locationString);
				user.setWorkLocation(location);
			} else {
				user.setWorkLocation(oldUser.getWorkLocation());
			}
			String password = request.getParameter("password");
			if (password != null) {
				user.setPassword(password);
			} else {
				user.setPassword(oldUser.getPassword());
			}
			user.setIcon(oldUser.getIcon());
			if (files != null) {
				// 获取图片
				for (int i = 0; i < files.length; i++) {
					if (files[i] != null) {
						// 取得当前上传文件的文件名称
						String myFileName = files[i].getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (myFileName.trim() != "") {
							// 重命名上传后的文件名
							String imageRootDir = "image/";
							Date date = new Date();
							String imageFileName = null;
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyyMMddHHmmss");
							imageFileName = sdf.format(date);
							String imageDir = imageRootDir + imageFileName
									+ ".jpg";
							String curRealPath = request.getSession()
									.getServletContext().getRealPath("/");
							System.out.println(curRealPath);
							// 定义上传路径
							String imagePath = curRealPath + imageDir;
							System.out.println(imagePath);
							File localFile = new File(imagePath);
							files[i].transferTo(localFile);
							Image img = ImageIO.read(new File(imagePath));
							// 判断图片格式是否正确
							int newWidth;
							int newHeight;
							// 为等比缩放计算输出的图片宽度及高度
							double rate1 = ((double) img.getWidth(null))
									/ (double) 1000 + 0.1;
							double rate2 = ((double) img.getHeight(null))
									/ (double) 1000 + 0.1;
							// 根据缩放比率大的进行缩放控制
							double rate = rate1 > rate2 ? rate1 : rate2;
							newWidth = (int) (((double) img.getWidth(null)) / rate);
							newHeight = (int) (((double) img.getHeight(null)) / rate);
							BufferedImage tag = new BufferedImage(
									(int) newWidth, (int) newHeight,
									BufferedImage.TYPE_INT_RGB);

							/*
							 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高
							 * 生成的图片质量比较好 但速度慢
							 */
							tag.getGraphics().drawImage(
									img.getScaledInstance(newWidth, newHeight,
											Image.SCALE_SMOOTH), 0, 0, null);
							ImageIO.write(tag, "jpg", new File(imagePath));
							user.setIcon(imageFileName);
						}
					}
				}
			}
			userService.updateUser(user);
			ret.put("state", 0);
			ret.put("profile", Constant.IMAGE_PATH + user.getIcon() + ".jpg");
			ret.put("info", "success");
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("profile", Constant.IMAGE_PATH + "default.jpg");
			ret.put("info", e.getMessage());
			e.printStackTrace();
		}
		response.getWriter().write(ret.toString());
	}
}
