import pydot
import os
from IPython.display import Image, display

rootDir = "Root"
G = pydot.Dot(graph_type="digraph")

node = pydot.Node(rootDir, style="filled", fillcolor="green")
G.add_node(node)
img = Image(G.create_png())
display(img)
