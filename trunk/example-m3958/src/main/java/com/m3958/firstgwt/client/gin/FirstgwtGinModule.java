package com.m3958.firstgwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.BlankAvatar;
import com.m3958.firstgwt.client.layout.AddressEditLayout;
import com.m3958.firstgwt.client.layout.AppToolStrip;
import com.m3958.firstgwt.client.layout.ArticleEditLayout;
import com.m3958.firstgwt.client.layout.ArticleLayout;
import com.m3958.firstgwt.client.layout.AssetEditLayout;
import com.m3958.firstgwt.client.layout.AssetFolderEditLayout;
import com.m3958.firstgwt.client.layout.AssetFolderLayout;
import com.m3958.firstgwt.client.layout.AssetLayout;
import com.m3958.firstgwt.client.layout.AssetUploadLayout;
import com.m3958.firstgwt.client.layout.CareerEditLayout;
import com.m3958.firstgwt.client.layout.CommentEditLayout;
import com.m3958.firstgwt.client.layout.CommentLayout;
import com.m3958.firstgwt.client.layout.DefaultMainMenuLayout;
import com.m3958.firstgwt.client.layout.DefaultTopLayout;
import com.m3958.firstgwt.client.layout.DepartmentEditLayout;
import com.m3958.firstgwt.client.layout.DepartmentLayout;
import com.m3958.firstgwt.client.layout.DiskFileEditLayout;
import com.m3958.firstgwt.client.layout.DiskFileLayout;
import com.m3958.firstgwt.client.layout.FamilyEditLayout;
import com.m3958.firstgwt.client.layout.FeedbackEditLayout;
import com.m3958.firstgwt.client.layout.FeedbackLayout;
import com.m3958.firstgwt.client.layout.FieldEnumEditLayout;
import com.m3958.firstgwt.client.layout.FieldEnumLayout;
import com.m3958.firstgwt.client.layout.FtlEditLayout;
import com.m3958.firstgwt.client.layout.FtlLayout;
import com.m3958.firstgwt.client.layout.GroupEditLayout;
import com.m3958.firstgwt.client.layout.GroupLayout;
import com.m3958.firstgwt.client.layout.HgllEditLayout;
import com.m3958.firstgwt.client.layout.HgllLayout;
import com.m3958.firstgwt.client.layout.HmessageReceiveLayout;
import com.m3958.firstgwt.client.layout.HouseEditLayout;
import com.m3958.firstgwt.client.layout.HtmlCssEditLayout;
import com.m3958.firstgwt.client.layout.HtmlCssLayout;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.layout.JrxmlFileEditLayout;
import com.m3958.firstgwt.client.layout.JrxmlFileLayout;
import com.m3958.firstgwt.client.layout.LeftStrip;
import com.m3958.firstgwt.client.layout.LgbEditLayout;
import com.m3958.firstgwt.client.layout.LgbLayout;
import com.m3958.firstgwt.client.layout.LinkEditLayout;
import com.m3958.firstgwt.client.layout.LinkLayout;
import com.m3958.firstgwt.client.layout.LoginLayout;
import com.m3958.firstgwt.client.layout.MenuItemEditLayout;
import com.m3958.firstgwt.client.layout.MenuItemLayout;
import com.m3958.firstgwt.client.layout.MenuLevelEditLayout;
import com.m3958.firstgwt.client.layout.MenuLevelLayout;
import com.m3958.firstgwt.client.layout.ObjectClassEditLayout;
import com.m3958.firstgwt.client.layout.ObjectClassLayout;
import com.m3958.firstgwt.client.layout.PageMistakeEditLayout;
import com.m3958.firstgwt.client.layout.PageMistakeLayout;
import com.m3958.firstgwt.client.layout.RegisteLayout;
import com.m3958.firstgwt.client.layout.RewardEditLayout;
import com.m3958.firstgwt.client.layout.RightStrip;
import com.m3958.firstgwt.client.layout.RoleEditLayout;
import com.m3958.firstgwt.client.layout.RoleLayout;
import com.m3958.firstgwt.client.layout.SectionEditLayout;
import com.m3958.firstgwt.client.layout.SectionLayout;
import com.m3958.firstgwt.client.layout.ShequEditLayout;
import com.m3958.firstgwt.client.layout.ShequLayout;
import com.m3958.firstgwt.client.layout.SiteConfigEditLayout;
import com.m3958.firstgwt.client.layout.SiteConfigLayout;
import com.m3958.firstgwt.client.layout.StepCareerEditLayout;
import com.m3958.firstgwt.client.layout.Strip;
import com.m3958.firstgwt.client.layout.ToolsLayout;
import com.m3958.firstgwt.client.layout.UserEditLayout;
import com.m3958.firstgwt.client.layout.UserLayout;
import com.m3958.firstgwt.client.layout.VoteEditLayout;
import com.m3958.firstgwt.client.layout.VoteHitLayout;
import com.m3958.firstgwt.client.layout.VoteLayout;
import com.m3958.firstgwt.client.layout.WebHostEditLayout;
import com.m3958.firstgwt.client.layout.WebHostLayout;
import com.m3958.firstgwt.client.layout.WebSiteEditLayout;
import com.m3958.firstgwt.client.layout.WebSiteLayout;
import com.m3958.firstgwt.client.layout.WelcomeLayout;
import com.m3958.firstgwt.client.layout.XinJianCatEditLayout;
import com.m3958.firstgwt.client.layout.XinJianCatLayout;
import com.m3958.firstgwt.client.layout.XinJianEditLayout;
import com.m3958.firstgwt.client.layout.XinJianLayout;
import com.m3958.firstgwt.client.service.GwtRPCServiceAsync;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.grid.ListGrid;


/**
 * 
 * @author jianglibo
 * 只有在容器无法确切知道内容layout的情况下，才用Named标注，如果知道确切知道内容物，
 * 则没有必要命名，直接注入即可。
 *
 */


public class FirstgwtGinModule extends AbstractGinModule{

	@Override
	protected void configure() {

		bind(Image.class).annotatedWith(LogoImg.class)
			.toProvider(LogoImgProvider.class);
		
		bind(GwtRPCServiceAsync.class).in(Singleton.class);
		
		bind(AppStatusService.class).in(Singleton.class);
		
		bind(ListGrid.class).annotatedWith(MainMenuGrid.class).toProvider(MainMenuGridProvider.class);
		
		bind(AppToolStrip.class).annotatedWith(Names.named("strip")).to(Strip.class);
		bind(AppToolStrip.class).annotatedWith(Names.named("leftStrip")).to(LeftStrip.class);
		bind(AppToolStrip.class).annotatedWith(Names.named("rightStrip")).to(RightStrip.class);
		
		bind(AssetUploadLayout.class).annotatedWith(Names.named(ViewNameEnum.ARTICLE_EDIT.toString())).toProvider(ArticleAssetUploadLayoutProvider.class);
		bind(AssetUploadLayout.class).annotatedWith(Names.named(ViewNameEnum.XINJIAN_EDIT.toString())).toProvider(XinJianAssetUploadLayoutProvider.class);
		bind(AssetUploadLayout.class).annotatedWith(Names.named(ViewNameEnum.LGB_EDIT.toString())).toProvider(LgbAssetUploadLayoutProvider.class);
		bind(AssetUploadLayout.class).annotatedWith(Names.named(ViewNameEnum.ASSET.toString())).toProvider(AssetUploadLayoutProvider.class);
		bind(AssetUploadLayout.class).annotatedWith(Names.named(ViewNameEnum.DISKFILE.toString())).toProvider(DiskFileUploadLayoutProvider.class);
		
		bind(Img.class).annotatedWith(Names.named("blankAvatar")).to(BlankAvatar.class);
		
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ADDRESS_EDIT.toString())).to(AddressEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ARTICLE.toString())).to(ArticleLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ARTICLE_EDIT.toString())).to(ArticleEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ASSET.toString())).to(AssetLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ASSET_EDIT.toString())).to(AssetEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ASSET_FOLDER.toString())).to(AssetFolderLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ASSET_FOLDER_EDIT.toString())).to(AssetFolderEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.CAREER_EDIT.toString())).to(CareerEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.DEPARTMENT.toString())).to(DepartmentLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.DEPARTMENT_EDIT.toString())).to(DepartmentEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.DISKFILE.toString())).to(DiskFileLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.DISKFILE_EDIT.toString())).to(DiskFileEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.FAMILY_EDIT.toString())).to(FamilyEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.FEED_BACK.toString())).to(FeedbackLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.FEEDBACK_EDIT.toString())).to(FeedbackEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.FIELD_ENUM.toString())).to(FieldEnumLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.FIELD_ENUM_EIDT.toString())).to(FieldEnumEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.FTL.toString())).to(FtlLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.FTL_EDIT.toString())).to(FtlEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.GROUP.toString())).to(GroupLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.GROUP_EDIT.toString())).to(GroupEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.HOUSE_EDIT.toString())).to(HouseEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.JRXML.toString())).to(JrxmlFileLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.JRXML_EDIT.toString())).to(JrxmlFileEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.LGB.toString())).to(LgbLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.LGB_EDIT.toString())).to(LgbEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.LINK.toString())).to(LinkLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.LINK_EDIT.toString())).to(LinkEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.VOTE.toString())).to(VoteLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.VOTE_EDIT.toString())).to(VoteEditLayout.class).in(Singleton.class);
		
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.VOTEHIT.toString())).to(VoteHitLayout.class).in(Singleton.class);
		
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.LOGIN.toString())).to(LoginLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.MENUITEM.toString())).to(MenuItemLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.MENUITEM_EDIT.toString())).to(MenuItemEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.MENULEVEL.toString())).to(MenuLevelLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.MENULEVEL_EDIT.toString())).to(MenuLevelEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.MMENU.toString())).to(DefaultMainMenuLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.OBJECT_CLASS.toString())).to(ObjectClassLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.OBJECT_CLASS_EDIT.toString())).to(ObjectClassEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.REGISTE.toString())).to(RegisteLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.REWARD_EDIT.toString())).to(RewardEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ROLE.toString())).to(RoleLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.ROLE_EDIT.toString())).to(RoleEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.SECTION.toString())).to(SectionLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.SECTION_EDIT.toString())).to(SectionEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.SHEQU.toString())).to(ShequLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.SHEQU_EDIT.toString())).to(ShequEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.SITE_CONFIG.toString())).to(SiteConfigLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.SITECONFIG_EDIT.toString())).to(SiteConfigEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.STEP_CAREER_EDIT.toString())).to(StepCareerEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.TOP.toString())).to(DefaultTopLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.TOOLS.toString())).to(ToolsLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.USER.toString())).to(UserLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.USER_EDIT.toString())).to(UserEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.WEBHOST.toString())).to(WebHostLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.WEBHOST_EDIT.toString())).to(WebHostEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.WEBSITE.toString())).to(WebSiteLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.WEBSITE_EDIT.toString())).to(WebSiteEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.WELCOME.toString())).to(WelcomeLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.HGLL.toString())).to(HgllLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.HGLL_EDIT.toString())).to(HgllEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.HTMLCSS.toString())).to(HtmlCssLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.HTMLCSS_EDIT.toString())).to(HtmlCssEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.XJ_CAT.toString())).to(XinJianCatLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.XJ_CAT_EDIT.toString())).to(XinJianCatEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.XINJIAN.toString())).to(XinJianLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.XINJIAN_EDIT.toString())).to(XinJianEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.COMMENT.toString())).to(CommentLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.COMMENT_EDIT.toString())).to(CommentEditLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.PAGE_MISTAKE.toString())).to(PageMistakeLayout.class).in(Singleton.class);
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.PAGE_MISTAKE_EDIT.toString())).to(PageMistakeEditLayout.class).in(Singleton.class);
		
		bind(Iview.class).annotatedWith(Names.named(ViewNameEnum.HMESSAGE_RECEIVE.toString())).to(HmessageReceiveLayout.class).in(Singleton.class);

	}

}
