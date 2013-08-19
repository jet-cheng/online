package com.wini.online.processor;

import java.util.Map;

import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;

import com.wini.online.render.FrontRender;

@RequestProcessor
public class IndexProcessor {

	@RequestProcessing(value = {"/index.wini"},method = HTTPRequestMethod.GET)
	public void index(final HTTPRequestContext context) throws Exception{
		final AbstractFreeMarkerRenderer renderer = new FrontRender();
        context.setRenderer(renderer);
        renderer.setTemplateName("index.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();
        dataModel.put("welcome", "欢迎进入winionline平台");
	}
}
