
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
            .domain([1, 2, 3, 4, 5, 6])
            .range(["#339933","#33cc33","#cc0000","#ff6666","#e4d4ef","#efe4d4"]);

    //init force
    var force = d3.layout.force()
            .charge(-150)
            .linkDistance(20)
            .size([width, height]);


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


    //read in data
    d3.json("rna/complete_5s.json", function(error, graph) {
        var nodes = graph.nodes;

        var links = function () {
            a = [];
            for (var i = 0; i < nodes.length-1; i++) {
                a.push({"source":i,"target":i+1,"value":2})
            }
            return a;
        }();

        force
                .nodes(nodes)
                .links(links)
                .start();

        var link = svg.selectAll(".link")
                .data(links)
                .enter().append("line")
                .attr("class", "link")
                .style("stroke-width", function(d) { return Math.sqrt(d.value); });

        var node = svg.selectAll(".node")
                .data(graph.nodes)
                .enter().append("circle")
                .attr("class", "node")
                .attr("r", 5)
                .style("fill", function(d) { return color(d.group); })
                .call(force.drag);

        force.on("tick", function(e) {
            link
                    .attr("x1", function(d) { return d.source.x; })
                    .attr("y1", function(d) { return d.source.y; })
                    .attr("x2", function(d) { return d.target.x; })
                    .attr("y2", function(d) { return d.target.y; });

            node
                    .attr("cx", function(d) { return d.x; })
                    .attr("cy", function(d) { return d.y; });
        });
    });



    // line displayed when dragging new nodes
    var drag_line = svg.append("line")
            .attr("class", "drag_line")
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
                .attr("x2", d3.svg.mouse(this)[0])
                .attr("y2", d3.svg.mouse(this)[1]);

    }

    function mouseup() {
        if (mousedown_node) {
            // hide drag line
            drag_line
                    .attr("class", "drag_line_hidden");

            if (!mouseup_node) {
                // add node
                var point = d3.mouse(this),
                        node = {x: point[0], y: point[1]},
                        n = nodes.push(node);

                // select new node
                selected_node = node;
                selected_link = null;

                // add link to mousedown node
                links.push({source: mousedown_node, target: node});
            }

            redraw();
        }
        // clear mouse event vars
        resetMouseVars();
        console.log("mouse up!");
    }

    function resetMouseVars() {
        mousedown_node = null;
        mouseup_node = null;
        mousedown_link = null;
    }

    // redraw force layout
    function redraw() {
        console.log("redrawing");
        link = link.data(links);
        link.enter().insert("line", ".node")
                .attr("class", "link")
                .on("mousedown",
                function(d) {
                    console.log("mouse down on link");
                    mousedown_link = d;
                    if (mousedown_link == selected_link) selected_link = null;
                    else selected_link = mousedown_link;
                    selected_node = null;
                    redraw();
                });

        link.exit().remove();

        link
                .classed("link_selected", function(d) { return d === selected_link; });

        node = node.data(nodes);
        console.log(node.enter());
        node.enter().insert("circle")
                .attr("class", "node")
                .attr("r", 5)
                .on("mousedown",
                function(d) {
                    // disable zoom
                    console.log("Node clicked");
                    svg.call(d3.behavior.zoom().on("zoom"), null);

                    mousedown_node = d;
                    if (mousedown_node == selected_node) selected_node = null;
                    else selected_node = mousedown_node;
                    selected_link = null;

                    // reposition drag line
                    drag_line
                            .attr("class", "link")
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
                    if (mousedown_node) {
                        mouseup_node = d;
                        if (mouseup_node == mousedown_node) { resetMouseVars(); return; }

                        // add link
                        var link = {source: mousedown_node, target: mouseup_node};
                        links.push(link);

                        // select new link
                        selected_link = link;
                        selected_node = null;

                        // enable zoom
                        //svg.call(d3.behavior.zoom().on("zoom"), rescale);
                        redraw();
                    }
                })
                .transition()
                .duration(750)
                .ease("elastic")
                .attr("r", 6.5);

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
        console.log("End of redraw");

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
                if (selected_node) {
                    nodes.splice(nodes.indexOf(selected_node), 1);
                    spliceLinksForNode(selected_node);
                }
                else if (selected_link) {
                    links.splice(links.indexOf(selected_link), 1);
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

