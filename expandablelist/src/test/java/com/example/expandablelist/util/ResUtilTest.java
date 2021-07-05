package com.example.expandablelist.util;


import java.io.IOException;
import java.util.Optional;
import ohos.app.Context;
import ohos.global.resource.Element;
import ohos.global.resource.NotExistException;
import ohos.global.resource.ResourceManager;
import ohos.global.resource.WrongTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import ohos.media.image.PixelMap;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResUtilTest {

    @InjectMocks
    ResUtil resUtil;
    @Mock
    Context context;
    @Mock
    ResourceManager resourceManager;
    @Mock
    Element element;

    @Test
    public void testgetPathWithContextNUll() {
        String res= ResUtil.getPathById(null,0);
        assertEquals("",res);
    }

    @Test
    public void testgetPathWithManagerNUll() {
        when(context.getResourceManager()).thenReturn(null);
        String res= ResUtil.getPathById(context,0);
        assertEquals("",res);
    }

    @Test
    public void testgetPathForVerify() {
        when(context.getResourceManager()).thenReturn(resourceManager);
        ResUtil.getPathById(context,0);
        try {
            verify(resourceManager,atLeastOnce()).getMediaPath(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetColorWithContextNUll() {
        int res= ResUtil.getColor(null,0);
        assertEquals(0,res);
    }

    @Test
    public void testgetColorWithManagerNUll() {
        when(context.getResourceManager()).thenReturn(null);
        int res= ResUtil.getColor(context,0);
        assertEquals(0,res);
    }


    @Test
    public void testgetColorForVerify() {
        when(context.getResourceManager()).thenReturn(resourceManager);
        try {
            when(resourceManager.getElement(0)).thenReturn(element);
            ResUtil.getColor(context,0);
            verify(resourceManager,atLeastOnce()).getElement(0);
            verify(element,atLeastOnce()).getColor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testgetDimenWithContextNUll() {
        float res= ResUtil.getDimen(null,0);
        assertEquals(0,res,0);
    }

    @Test
    public void testgetDimenWithManagerNUll() {
        when(context.getResourceManager()).thenReturn(null);
        float res= ResUtil.getDimen(context,0);
        assertEquals(0,res,0);
    }

    @Test
    public void testgetDimenForVerify() {
        when(context.getResourceManager()).thenReturn(resourceManager);
        try {
            when(resourceManager.getElement(0)).thenReturn(element);
            ResUtil.getDimen(context,0);
            verify(resourceManager,atLeastOnce()).getElement(0);
            verify(element,atLeastOnce()).getFloat();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetIntDimenWithZero(){
        when(context.getResourceManager()).thenReturn(resourceManager);
        try {
            when(resourceManager.getElement(0)).thenReturn(element);
            when(element.getFloat()).thenReturn((float)0.4);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
        int res= ResUtil.getIntDimen(context,0);
        assertEquals(0,res);
    }

    @Test
    public void testgetIntDimenwithNonZero(){
        when(context.getResourceManager()).thenReturn(resourceManager);
        try {
            when(resourceManager.getElement(0)).thenReturn(element);
            when(element.getFloat()).thenReturn((float)1.6);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
        int res= ResUtil.getIntDimen(context,0);
        assertEquals(2,res);
    }

    @Test
    public void testgetStringWithContextNUll() {
        String res= ResUtil.getString(null,0);
        assertEquals("",res);
    }

    @Test
    public void testgetStringWithManagerNUll() {
        when(context.getResourceManager()).thenReturn(null);
        String res= ResUtil.getString(context,0);
        assertEquals("",res);
    }

    @Test
    public void testgetStringForVerify() {
        when(context.getResourceManager()).thenReturn(resourceManager);
        try {
            when(resourceManager.getElement(0)).thenReturn(element);
            ResUtil.getString(context,0);
            verify(resourceManager,atLeastOnce()).getElement(0);
            verify(element,atLeastOnce()).getString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetPixelMapWithContextNUll() {
        Optional<PixelMap> res= ResUtil.getPixelMap(null,0);
        assertEquals( Optional.empty(),res);
    }

    @Test
    public void testgetPixelMapWithContext() {
        ResUtil.getPixelMap(context,0);
        verify(context, atLeastOnce()).getResourceManager();
    }

}