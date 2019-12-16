
[apk 下载](https://github.com/zhengzaihong/MyViews/blob/master/Screenshots/app-debug.apk)


项目依赖：

使用AndroidX包的请添加如下依赖：

``` 'implementation 'com.zzh:viewtoolsX:xxx'(版本号)' ``` 

使用support包的请添加如下依赖

``` implementation 'com.zzh.viewtools:xxx(版本号)'  ```//最新查看version_xxx.txt信息


*注意：本库使用到Androidx库 和SDK 28以下的区别，请分别正确引入 *



样列图，更多请下载apk或者查看源码



##更新日志 2019-12-13

新特性 TableView 控件支持特殊列宽度控制，用法如下

    //用于控制特殊列的宽度
    ColumnController controller = new ColumnController();
    //单位注意转换成 dp  参数1：第几列   参数2:第几列的宽度
    //文字过多建议配合自适应高度属性使用
    
    controller.addSpecial(0, 400)
              .addSpecial(3, 200);
    
    //配置控制器
    tableView.setColumnController(controller);


新增 支持圆角和按压背景切换的 RadiusLinearLayout和RadiusRelateLayout

新增 RadioButtonPlus 圆角和背景切换以及文字颜色切换的 RadioButton

其他优化...


##更新日志 2019-12-4


新特性 TableView控件 支持自适应内容高度，性能优化。支持布局文件配置,使用更简洁.

如果TableView 是需要编辑的且需要编辑后值，则TableView 的 如下两个属性必须为true,否则第一行数据将丢失

        app:table_visible_head="true"
        app:table_need_edit="true"
        
        或者代码配置
        
        setEditTable(true)
        
        setShowHead(true)


获取值的需要特殊处理下，因为TableView 为了灵活性并没限制每个单元格使用什么View,即可以是任何View,因此内部不能帮你采集出编辑后的值，
所以需要你自己手动处理下，如下：

  
    btGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseArray<LinkedHashSet<View>> dataViews = tableView.getDataView();

                for (int i = 0; i < dataViews.size(); i++) {
                    LinkedHashSet<View> views = dataViews.get(i);
                    if (null != views) {
                        StringBuffer buffer = new StringBuffer("--------");
                        Iterator<View> iterator = views.iterator();
                        while (iterator.hasNext()) {
                            TextView editText = iterator.next().findViewById(R.id.tvCell);
                            buffer.append(editText.getText().toString()).append("--");
                        }
                        outRedPrint("一行信息：" + buffer.toString());
                    }
                }
            }
        });


                                                  

新增 支持动画 GifImageView的控件

新增 加载动画新增API 

新增 支持渐变文字方向的 GradientTextView

其他优化


##更新日志 2019-11-15

新增 表格控件 Api,替换数据

新增 EditextView 限制数据过滤器 EditInputFilter 和 ValueFilter

新增 ListView 5种动画显示效果  具体看 UtilAdapter

新增 CommonAdapter Api 支持图片和 文本颜色链式调用


##更新日志 2019-7-22

1.时间类控件（验证码倒计时，定时任务,任务队列等）

2.秒杀类控件

3.跑马灯控件（上下左右皆可）

4.跑马灯控件之电影谢幕效果

5.开关按钮控件

6.仿造ios圆形菜单控件（实现了任意角度圆角）

7.仿ios系统提示框。

8.相册提示框

9.加载提示框（可设置加载时间以及内容）

10.转动的ImageView

11.圆形且支持边框的ImageView

12.支持圆角的ImageView

13.圆环进度view

14.表格控件

15.简易日历

16.ObservableScrollView( 支持测量滑动距离的和状态的ScrollView)

17.FilletView(支持任意边圆角的View,保留TextView全部特性，减少开发中大量的写xml配置文件来改变背景色文字颜色等)

18.MyTouchViewPager(支持界面中可以横向滑动切换导致ViewPager的滑动失效问题)

19.NoScrollViewPager(支持是否可以滑动的ViewPager)

20.UnderLineTextView(支持下划线的TexTView)

