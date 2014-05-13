 myPanel = new jsgl.Panel(document.getElementById("panel"));
 
/* Define objects */

var init = function (shape) {
  shape.getStroke().setWeight(0);
  shape.getFill().setColor("#F49B31");
  myPanel.addElement(shape);
};
var update = function (shape, cX, cY, h, w)  {
  shape.clearPoints();
  shape.addPointXY(cX, cY);
  shape.addPointXY(cX + h/2.0, cY + w/3);
  shape.addPointXY(cX + h/2, cY + w);
  shape.addPointXY(cX - h/2, cY + w);
  shape.addPointXY(cX - h/2, cY + w/3);
};

first = myPanel.createPolygon();
init(first);
update(first,200,200,60,100);
first.addMouseDownListener(function () {
  first.click = true;;        
});
first.addMouseUpListener(function () {
  first.click = false;
});
first.addMouseMoveListener(function (eventArgs) {
  if (first.click) {
    update(first,eventArgs.getX(),eventArgs.getY(),60,100);
  }
});

/* Create polygon and modify it */
polygon = myPanel.createPolygon();
polygon.addPointXY(50,50);
polygon.addPointXY(110,50);
polygon.addPointXY(150,80);
polygon.addPointXY(110,110);
polygon.addPointXY(50,110);
polygon.getStroke().setWeight(5);
polygon.getStroke().setColor("#015BC2");
polygon.getFill().setColor("#015BC2");
myPanel.addElement(polygon);

polygon2 = myPanel.createPolygon();
polygon2.addPointXY(110,50);
polygon2.addPointXY(210,50);
polygon2.addPointXY(210,110);
polygon2.addPointXY(110,110);
polygon2.addPointXY(150,80);
polygon2.getStroke().setWeight(5);
polygon2.getStroke().setColor("#83389C");
polygon2.getFill().setColor("#83389C");
polygon2.addMouseDownListener(function () {
  polygon2.getFill().setOpacity(0.5);        
});
polygon2.addMouseUpListener(function () {
  polygon2.getFill().setOpacity(1.0);
});
myPanel.addElement(polygon2);

circle = myPanel.createCircle();
circle.setCenterLocationXY(100,100);
circle.setRadius(20);
circle.getFill().setColor("#FCD333");
circle.addMouseDownListener(function () {
  circle.click = true;;        
});
circle.addMouseUpListener(function () {
  circle.click = false;
});
circle.addMouseMoveListener(function (eventArgs) {
  if (circle.click) {
    circle.setCenterLocationXY(eventArgs.getX(),eventArgs.getY());
  }
});
myPanel.addElement(circle);

 

 