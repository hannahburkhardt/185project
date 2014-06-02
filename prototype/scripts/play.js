//-------------------------------------
//-------------button def-------------
//-------------------------------------
$(".submit").on("click",function (e) {
    console.log("Button clicked");
});


//-------------------------------------
//-------------getSeq part-------------
//-------------------------------------
var n = [];
var readSeq = function () {
    //console.log("calling");
    var input_seq = $(".inseq").val();
    if (input_seq === "") return;

    for (i in input_seq) {
        var l;
        switch (input_seq[i]) {
            case 'A':
            case 'a':
                l = 'a';
                break;
            case 'C':
            case 'c':
                l = 'c';
                break;
            case 'G':
            case 'g':
                l = 'g';
                break;
            case 'U':
            case 'u':
                l = 'u';
                break;
            default:
                l = 'a';
                break;
        }
        n.push({
            "base": l
        });
    }
    //console.log("returning: " + n);
};


$(".btn").on("click", function (event) {
    readSeq();
});

$(".btn").on("keypress", function (event) {
    if (event.keyCode === 13) //enter
        readSeq();
});






//-----------------------------------
//-------------play part-------------
//-----------------------------------

var width = 960,
    height = 500;

// mouse event vars
var selected_node = null,
    selected_link = null,
    mousedown_link = null,
    mousedown_node = null,
    mouseup_node = null;

//set colors
var color = d3.scale.ordinal()
    .domain(['a', 'u', 'g', 'c', 5, 6])
    .range(["#339933","#33cc33","#cc0000","#ff6666","#e4d4ef","#efe4d4"]);



// init svg
var outer = d3.select("body").append("svg:svg")
    .attr("width", width)
    .attr("height", height)
    .attr("pointer-events", "all");

var svg = outer.append("svg:g")
    //.call(d3.behavior.zoom().on("zoom", rescale))
    .on("mousemove", mousemove)
    .on("mousedown", mousedown)
    .on("mouseup", mouseup);
svg.append('svg:rect')
    .attr('width', width)
    .attr('height', height)
    .attr('fill', 'white');
// end init svg


var force = d3.layout.force()
    .size([width, height])
    .charge(-150)
    .linkDistance(function (d) { if (d.type===0) return 20; else return 10;});

//read in data
//d3.json("rna/hairpinloop_5s.json", function(error, graph) {
    //var nodes = graph.nodes;
    var nodes = [
        {base:"c",available:1},
        {base:"c",available:1},
        {base:"c",available:1},
        {base:"a",available:1},
        {base:"a",available:1},
        {base:"a",available:1},
        {base:"g",available:1},
        {base:"g",available:1},
        {base:"g",available:1}
    ];




    var links = function () {
        a = [];
        for (var i = 0; i < nodes.length - 1; i++) {
            a.push({source: i, target: i + 1, type:0})
        }
        return a;
    }();

    force
        .nodes(nodes)
        .links(links);


    force.on("tick", function (e) {
        link
            .attr("x1", function (d) {
                return d.source.x;
            })
            .attr("y1", function (d) {
                return d.source.y;
            })
            .attr("x2", function (d) {
                return d.target.x;
            })
            .attr("y2", function (d) {
                return d.target.y;
            });

        node
            .attr("cx", function (d) {
                return d.x;
            })
            .attr("cy", function (d) {
                return d.y;
            });
    })
        .start();


//        force
//                .nodes(nodes)
//                .links(links)
//

//        var link = svg.selectAll(".link")
//                .data(links)
//                .enter().append("line")
//                .attr("class", "link")
//                .style("stroke-width", function(d) { return Math.sqrt(d.value); });
//
//        var node = svg.selectAll(".node")
//                .data(nodes)
//                .enter().append("circle")
//                .attr("class", "node")
//                .attr("r", 5)
//                .style("fill", function(d) { return color(d.group); })
//                .call(force.drag);
//
//        force.on("tick", function(e) {
//            link
//                    .attr("x1", function(d) { return d.source.x; })
//                    .attr("y1", function(d) { return d.source.y; })
//                    .attr("x2", function(d) { return d.target.x; })
//                    .attr("y2", function(d) { return d.target.y; });
//
//            node
//                    .attr("cx", function(d) { return d.x; })
//                    .attr("cy", function(d) { return d.y; });
//        });



// line displayed when dragging new nodes
var drag_line = svg.append("line")
    .attr("class", "h-bond")
    .attr("x1", 0)
    .attr("y1", 0)
    .attr("x2", 0)
    .attr("y2", 0);

// get layout properties
var nodes = force.nodes(),
    links = force.links(),
    node = svg.selectAll(".node"),
    link = svg.selectAll(".link");

// add keyboard callback
d3.select(window)
    .on("keydown", keydown);
redraw();


function mousedown() {
    if (!mousedown_node && !mousedown_link) {
        // allow panning if nothing is selected
        /*console.log(svg);
         svg.call(d3.behavior.zoom().on("zoom"), rescale);

         return;*/
    }
}

function mousemove() {
    if (!mousedown_node) return;

    // update drag line
    drag_line
        .attr("x1", mousedown_node.x)
        .attr("y1", mousedown_node.y)
        .attr("x2", d3.mouse(this)[0])
        .attr("y2", d3.mouse(this)[1]);

}

function mouseup() {
    if (mousedown_node) {
        // hide drag line
        drag_line
            .attr("class", "drag_line_hidden");

        if (!mouseup_node) {
//            // add node
//            var point = d3.mouse(this),
//                node = {x: point[0], y: point[1]},
//                nodes.push(node);
//
//            // select new node
//            selected_node = node;
//            selected_link = null;
//
//            // add link to mousedown node
//            links.push({source: mousedown_node, target: node});
        }

        redraw();
    }
    // clear mouse event vars
    resetMouseVars();
    //console.log("mouse up!");
}

function resetMouseVars() {
    mousedown_node = null;
    mouseup_node = null;
    mousedown_link = null;
}

// redraw force layout
function redraw() {

    //console.log("redrawing");
    link = link.data(links);
    link.enter().insert("line", ".node")
        .attr("class", "link")
        .on("mousedown",
        function(d) {
            //console.log("mouse down on link");
            mousedown_link = d;
            if (mousedown_link == selected_link) selected_link = null;
            else selected_link = mousedown_link;
            selected_node = null;
            redraw();
        });

    link.exit().remove();

    link
        .classed("h_bond", function(d) { return d.type === 1; });
    force.linkDistance(function (d) { if (d.type===0) return 20; else return 3;});

    node = node.data(nodes);
    //console.log("line 229: "+node.enter());
    node.enter().insert("circle")
        .attr("class", "node")
        .attr("r", 6)
        .on("mousedown",
        function(d) {
            // disable zoom
            //console.log("Node clicked");
            //svg.call(d3.behavior.zoom().on("zoom"), null);

            mousedown_node = d;
            if (mousedown_node == selected_node) selected_node = null;
            else selected_node = mousedown_node;
            selected_link = null;

            // reposition drag line
            drag_line
                .attr("class", "h_bond")
                .attr("x1", mousedown_node.x)
                .attr("y1", mousedown_node.y)
                .attr("x2", mousedown_node.x)
                .attr("y2", mousedown_node.y);

            redraw();
        })
        .on("mousedrag",
        function(d) {
            // redraw();
        })
        .on("mouseup",
        function(d) {
            if (d.available) {
                if (mousedown_node && mousedown_node.available) {
                    mouseup_node = d;
                    if (mouseup_node == mousedown_node) { resetMouseVars(); return; }
                    if ( (mousedown_node.base == 'a' && mouseup_node.base == 'a') ||
                        (mousedown_node.base == 'u' && mouseup_node.base == 'a') ||
                        (mousedown_node.base == 'g' && mouseup_node.base == 'c') ||
                        (mousedown_node.base == 'c' && mouseup_node.base == 'g')  ) {
                        // add link
                        var link = {source: mousedown_node, target: mouseup_node, type:1};
                        links.push(link);

                        mousedown_node.available = 0;
                        mouseup_node.available = 0;

                        // select new link
                        selected_link = link;
                        selected_node = null;
                    }

                    // enable zoom
                    //svg.call(d3.behavior.zoom().on("zoom"), rescale);
                    redraw();
                }
            }
        })
        .transition()
        .duration(750)
        .ease("elastic")
        .style("fill", function(d) { return color(d.base); });

    node.exit().transition()
        .attr("r", 0)
        .remove();

    node
        .classed("node_selected", function(d) { return d === selected_node; });



    if (d3.event) {
        // prevent browser's default behavior
        d3.event.preventDefault();
    }

    force.start();
    //console.log("End of redraw");

}

function spliceLinksForNode(node) {
    toSplice = links.filter(
        function(l) {
            return (l.source === node) || (l.target === node); });
    toSplice.map(
        function(l) {
            links.splice(links.indexOf(l), 1); });
}

function keydown() {
    if (!selected_node && !selected_link) return;
    switch (d3.event.keyCode) {
        case 8: // backspace
        case 46: { // delete
            if (selected_link) {
                links.splice(links.indexOf(selected_link), 1);
                selected_link.source.available = 1;
                selected_link.target.available = 1;
            }
            selected_link = null;
            selected_node = null;
            redraw();
            break;
        }
    }
}



function rescale() {
    trans=d3.event.translate;
    scale=d3.event.scale;

    svg.attr("transform",
            "translate(" + trans + ")"
            + " scale(" + scale + ")");
}

