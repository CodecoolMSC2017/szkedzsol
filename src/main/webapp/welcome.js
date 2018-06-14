let canvas;
let context;
const textObjects = [];

// TextObject class
    function TextObject(x, y) {
      this.text = "FÁRAJÓ";
      this.x = x;
      this.y = y;

      this.draw = function () {
        // Move the text to the left with every draw call or resest it's position to the right
        this.x = this.x <= -150 ? canvas.width : this.x - 4;

        // Iterate through the letters in the word
        for (let ss = 0; ss < this.text.length; ss++) {
          // Set text style and call the drawing function on the context
          context.fillStyle = `hsl(${this.x / canvas.width * 360}, 100%, 50%)`;
          context.font = `${40 + Math.sin(this.x / 16) * 30}px monospace`;
          context.fillText(`${this.text[ss]}`, this.x + ss * 30, this.y);
        }
      }
    }

    // This runs every frame
    function update() {
      requestAnimationFrame(update);
      context.fillStyle = "#000";
      context.fillRect(0, 0, canvas.width, canvas.height);

      context.textBaseline = "middle";
      textObjects.forEach(textObject => {
        textObject.draw();
      });
    }

    function init(){
    // Container for the text objects

     canvas = document.getElementsByTagName("canvas")[0];
     context = canvas.getContext("2d");

    resizeCanvas();

    // Fill in the textObjects array with TextObject(s)
    const rows = 7, cols = 9;
    for (let r = 0; r < rows; r++) {
      for (let c = 0; c < cols; c++) {
        textObjects.push(
          new TextObject((r * 50) + ((canvas.width / cols) * c) + (c * 250), (canvas.height / rows) * r + 50)
        );
      }
    }
    }

    function resizeCanvas() {
        // Set the canvas to the windows's full size
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
    }

    // Start the loop
    function onWelcomeLoad() {
    clearMessages();
    showContents(['welcome-content']);
    update();
    }

document.addEventListener('DOMContentLoaded', init);
window.addEventListener('resize', resizeCanvas);
