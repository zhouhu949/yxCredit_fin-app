(function(E) {
    var H, F, G;
    H = (function() {
        function A(C, B, D) {
            var J;
            this.row = C;
            this.tree = B;
            this.settings = D;
            this.id = this.row.data(this.settings.nodeIdAttr);
            J = this.row.data(this.settings.parentIdAttr);
            if (J != null && J !== "") {
                this.parentId = J
            }
            this.treeCell = E(this.row.children(this.settings.columnElType)[this.settings.column]);
            this.expander = E(this.settings.expanderTemplate);
            this.indenter = E(this.settings.indenterTemplate);
            this.children = [];
            this.initialized = false;
            this.treeCell.prepend(this.indenter)
        }
        A.prototype.addChild = function(B) {
            return this.children.push(B)
        };
        A.prototype.ancestors = function() {
            var C, B;
            B = this;
            C = [];
            while (B = B.parentNode()) {
                C.push(B)
            }
            return C
        };
        A.prototype.collapse = function() {
            if (this.collapsed()) {
                return this
            }
            this.row.removeClass("expanded").addClass("collapsed");
            this._hideChildren();
            this.expander.attr("title", this.settings.stringExpand);
            if (this.initialized && this.settings.onNodeCollapse != null) {
                this.settings.onNodeCollapse.apply(this)
            }
            return this
        };
        A.prototype.collapsed = function() {
            return this.row.hasClass("collapsed")
        };
        A.prototype.expand = function() {
            if (this.expanded()) {
                return this
            }
            this.row.removeClass("collapsed").addClass("expanded");
            if (this.initialized && this.settings.onNodeExpand != null) {
                this.settings.onNodeExpand.apply(this)
            }
            if (E(this.row).is(":visible")) {
                this._showChildren()
            }
            this.expander.attr("title", this.settings.stringCollapse);
            return this
        };
        A.prototype.expanded = function() {
            return this.row.hasClass("expanded")
        };
        A.prototype.hide = function() {
            this._hideChildren();
            this.row.hide();
            return this
        };
        A.prototype.isBranchNode = function() {
            if (this.children.length > 0 || this.row.data(this.settings.branchAttr) === true) {
                return true
            } else {
                return false
            }
        };
        A.prototype.updateBranchLeafClass = function() {
            this.row.removeClass("branch");
            this.row.removeClass("leaf");
            this.row.addClass(this.isBranchNode() ? "branch": "leaf")
        };
        A.prototype.level = function() {
            return this.ancestors().length
        };
        A.prototype.parentNode = function() {
            if (this.parentId != null) {
                return this.tree[this.parentId]
            } else {
                return null
            }
        };
        A.prototype.removeChild = function(C) {
            var B = E.inArray(C, this.children);
            return this.children.splice(B, 1)
        };
        A.prototype.render = function() {
            var B, C = this.settings,
            D;
            if (C.expandable === true && this.isBranchNode()) {
                B = function(J) {
                    E(this).parents("table").treetable("node", E(this).parents("tr").data(C.nodeIdAttr)).toggle();
                    return J.preventDefault()
                };
                this.indenter.html(this.expander);
                D = C.clickableNodeNames === true ? this.treeCell: this.expander;
                D.off("click.treetable").on("click.treetable", B);
                D.off("keydown.treetable").on("keydown.treetable",
                function(J) {
                    if (J.keyCode == 13) {
                        B.apply(this, [J])
                    }
                })
            }
            this.indenter[0].style.paddingLeft = "" + (this.level() * C.indent) + "px";
            return this
        };
        A.prototype.reveal = function() {
            if (this.parentId != null) {
                this.parentNode().reveal()
            }
            return this.expand()
        };
        A.prototype.setParent = function(B) {
            if (this.parentId != null) {
                this.tree[this.parentId].removeChild(this)
            }
            this.parentId = B.id;
            this.row.data(this.settings.parentIdAttr, B.id);
            return B.addChild(this)
        };
        A.prototype.show = function() {
            if (!this.initialized) {
                this._initialize()
            }
            this.row.show();
            if (this.expanded()) {
                this._showChildren()
            }
            return this
        };
        A.prototype.toggle = function() {
            if (this.expanded()) {
                this.collapse()
            } else {
                this.expand()
            }
            return this
        };
        A.prototype._hideChildren = function() {
            var C, D, L, B, K;
            B = this.children;
            K = [];
            for (D = 0, L = B.length; D < L; D++) {
                C = B[D];
                K.push(C.hide())
            }
            return K
        };
        A.prototype._initialize = function() {
            var B = this.settings;
            this.render();
            if (B.expandable === true && B.initialState === "collapsed") {
                this.collapse()
            } else {
                this.expand()
            }
            if (B.onNodeInitialized != null) {
                B.onNodeInitialized.apply(this)
            }
            return this.initialized = true
        };
        A.prototype._showChildren = function() {
            var C, D, L, B, K;
            B = this.children;
            K = [];
            for (D = 0, L = B.length; D < L; D++) {
                C = B[D];
                K.push(C.show())
            }
            return K
        };
        return A
    })();
    F = (function() {
        function A(C, B) {
            this.table = C;
            this.settings = B;
            this.tree = {};
            this.nodes = [];
            this.roots = []
        }
        A.prototype.collapseAll = function() {
            var C, D, L, B, K;
            B = this.nodes;
            K = [];
            for (D = 0, L = B.length; D < L; D++) {
                C = B[D];
                K.push(C.collapse())
            }
            return K
        };
        A.prototype.expandAll = function() {
            var C, D, L, B, K;
            B = this.nodes;
            K = [];
            for (D = 0, L = B.length; D < L; D++) {
                C = B[D];
                K.push(C.expand())
            }
            return K
        };
        A.prototype.findLastNode = function(B) {
            if (B.children.length > 0) {
                return this.findLastNode(B.children[B.children.length - 1])
            } else {
                return B
            }
        };
        A.prototype.loadRows = function(D) {
            var C, J, B;
            if (D != null) {
                for (B = 0; B < D.length; B++) {
                    J = E(D[B]);
                    if (J.data(this.settings.nodeIdAttr) != null) {
                        C = new H(J, this.tree, this.settings);
                        this.nodes.push(C);
                        this.tree[C.id] = C;
                        if (C.parentId != null && this.tree[C.parentId]) {
                            this.tree[C.parentId].addChild(C)
                        } else {
                            this.roots.push(C)
                        }
                    }
                }
            }
            for (B = 0; B < this.nodes.length; B++) {
                C = this.nodes[B].updateBranchLeafClass()
            }
            return this
        };
        A.prototype.move = function(C, B) {
            var D = C.parentNode();
            if (C !== B && B.id !== C.parentId && E.inArray(C, B.ancestors()) === -1) {
                C.setParent(B);
                this._moveRows(C, B);
                if (C.parentNode().children.length === 1) {
                    C.parentNode().render()
                }
            }
            if (D) {
                D.updateBranchLeafClass()
            }
            if (C.parentNode()) {
                C.parentNode().updateBranchLeafClass()
            }
            C.updateBranchLeafClass();
            return this
        };
        A.prototype.removeNode = function(B) {
            this.unloadBranch(B);
            B.row.remove();
            if (B.parentId != null) {
                B.parentNode().removeChild(B)
            }
            delete this.tree[B.id];
            this.nodes.splice(E.inArray(B, this.nodes), 1);
            return this
        };
        A.prototype.render = function() {
            var B, C, D, J;
            J = this.roots;
            for (C = 0, D = J.length; C < D; C++) {
                B = J[C];
                B.show()
            }
            return this
        };
        A.prototype.sortBranch = function(B, C) {
            B.children.sort(C);
            this._sortChildRows(B);
            return this
        };
        A.prototype.unloadBranch = function(C) {
            var B = C.children.slice(0),
            D;
            for (D = 0; D < B.length; D++) {
                this.removeNode(B[D])
            }
            C.children = [];
            C.updateBranchLeafClass();
            return this
        };
        A.prototype._moveRows = function(C, B) {
            var D = C.children,
            J;
            C.row.insertAfter(B.row);
            C.render();
            for (J = D.length - 1; J >= 0; J--) {
                this._moveRows(D[J], C)
            }
        };
        A.prototype._sortChildRows = function(B) {
            return this._moveRows(B, B)
        };
        return A
    })();
    G = {
        init: function(A, B) {
            var C;
            C = E.extend({
                branchAttr: "ttBranch",
                clickableNodeNames: false,
                column: 0,
                columnElType: "td",
                expandable: false,
                expanderTemplate: "<a href='#'>&nbsp;</a>",
                indent: 19,
                indenterTemplate: "<span class='indenter'></span>",
                initialState: "collapsed",
                nodeIdAttr: "ttId",
                parentIdAttr: "ttParentId",
                stringExpand: "Expand",
                stringCollapse: "Collapse",
                onInitialized: null,
                onNodeCollapse: null,
                onNodeExpand: null,
                onNodeInitialized: null
            },
            A);
            return this.each(function() {
                var D = E(this),
                J;
                if (B || D.data("treetable") === undefined) {
                    J = new F(this, C);
                    J.loadRows(this.rows).render();
                    D.addClass("treetable").data("treetable", J);
                    if (C.onInitialized != null) {
                        C.onInitialized.apply(J)
                    }
                }
                return D
            })
        },
        destroy: function() {
            return this.each(function() {
                return E(this).removeData("treetable").removeClass("treetable")
            })
        },
        collapseAll: function() {
            this.data("treetable").collapseAll();
            return this
        },
        collapseNode: function(A) {
            var B = this.data("treetable").tree[A];
            if (B) {
                B.collapse()
            } else {
                throw new Error("Unknown node '" + A + "'")
            }
            return this
        },
        expandAll: function() {
            this.data("treetable").expandAll();
            return this
        },
        expandNode: function(A) {
            var B = this.data("treetable").tree[A];
            if (B) {
                if (!B.initialized) {
                    B._initialize()
                }
                B.expand()
            } else {
                throw new Error("Unknown node '" + A + "'")
            }
            return this
        },
        loadBranch: function(A, B) {
            var J = this.data("treetable").settings,
            D = this.data("treetable").tree;
            B = E(B);
            if (A == null) {
                this.append(B)
            } else {
                var C = this.data("treetable").findLastNode(A);
                B.insertAfter(C.row)
            }
            this.data("treetable").loadRows(B);
            B.filter("tr").each(function() {
                D[E(this).data(J.nodeIdAttr)].show()
            });
            if (A != null) {
                A.render().expand()
            }
            return this
        },
        move: function(D, B) {
            var C, A;
            A = this.data("treetable").tree[D];
            C = this.data("treetable").tree[B];
            this.data("treetable").move(A, C);
            return this
        },
        node: function(A) {
            return this.data("treetable").tree[A]
        },
        removeNode: function(A) {
            var B = this.data("treetable").tree[A];
            if (B) {
                this.data("treetable").removeNode(B)
            } else {
                throw new Error("Unknown node '" + A + "'")
            }
            return this
        },
        reveal: function(A) {
            var B = this.data("treetable").tree[A];
            if (B) {
                B.reveal()
            } else {
                throw new Error("Unknown node '" + A + "'")
            }
            return this
        },
        sortBranch: function(B, D) {
            var A = this.data("treetable").settings,
            C,
            J;
            D = D || A.column;
            J = D;
            if (E.isNumeric(D)) {
                J = function(Q, P) {
                    var O, I, R;
                    O = function(L) {
                        var K = L.row.find("td:eq(" + D + ")").text();
                        return E.trim(K).toUpperCase()
                    };
                    I = O(Q);
                    R = O(P);
                    if (I < R) {
                        return - 1
                    }
                    if (I > R) {
                        return 1
                    }
                    return 0
                }
            }
            this.data("treetable").sortBranch(B, J);
            return this
        },
        unloadBranch: function(A) {
            this.data("treetable").unloadBranch(A);
            return this
        }
    };
    E.fn.treetable = function(A) {
        if (G[A]) {
            return G[A].apply(this, Array.prototype.slice.call(arguments, 1))
        } else {
            if (typeof A === "object" || !A) {
                return G.init.apply(this, arguments)
            } else {
                return E.error("Method " + A + " does not exist on jQuery.treetable")
            }
        }
    };
    this.TreeTable || (this.TreeTable = {});
    this.TreeTable.Node = H;
    this.TreeTable.Tree = F
})(jQuery);