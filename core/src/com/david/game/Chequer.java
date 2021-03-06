package com.david.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.david.game.control.Board;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;

public class Chequer  extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;


    private int width;
    private int height;

    public PerspectiveCamera cam;

    private Environment environment;

    CameraInputController controller;
    public ModelBatch modelBatch;
    public ModelInstance space;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    private ControllerListener listener = new ControllerAdapter() {
        @Override
        public boolean buttonDown(Controller controller, int buttonIndex) {

            return true;
        }

        @Override
        public boolean buttonUp(Controller controller, int buttonIndex) {

            return true;
        }
    };
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        cam = new PerspectiveCamera(67, width, height);
        initCamera(cam);
        cam.update();

      createBalls();
        environment=createEnvironment();

        controller=createController();

        Gdx.input.setInputProcessor(new InputMultiplexer( controller));

        modelBatch = new ModelBatch();

        Gdx.gl.glViewport(0, 0, width, height);
        Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void render () {


        controller.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        if (space != null)
            modelBatch.render(space);
        modelBatch.end();
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}


    @Override
    public void dispose() {
        super.dispose();
        instances.clear();
        modelBatch.dispose();
    }


    private void initCamera(PerspectiveCamera cam)
    {

        cam.position.set(0f, 0f, 15f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;

    }


    private  Environment createEnvironment()
    {

        Environment     environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, 0f, -10f));

        return environment;
    }


    private void createBalls()
    {

        float ScaleSize=0.5f;

        Board board=new Board();
        board.init();
        int length=board.cells.length;
        int radius=(length+1)/2;

        ModelBuilder modelBuilder = new ModelBuilder();
        Model    redBall = modelBuilder.createSphere(1f*ScaleSize, 1f*ScaleSize, 1f*ScaleSize, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        Model    greenBall = modelBuilder.createSphere(1f*ScaleSize, 1f*ScaleSize, 1f*ScaleSize, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        Model    yellowBall = modelBuilder.createSphere(1f*ScaleSize, 1f*ScaleSize, 1f*ScaleSize, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        Model    pingBall = modelBuilder.createSphere(1f*ScaleSize, 1f*ScaleSize, 1f*ScaleSize, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        Model    grayBall = modelBuilder.createSphere(1f*ScaleSize, 1f*ScaleSize, 1f*ScaleSize, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);


        Model    blackBall = modelBuilder.createSphere(1f*ScaleSize, 1f*ScaleSize, 1f*ScaleSize, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.BLACK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);


        Model    whiteBall = modelBuilder.createSphere(1f*ScaleSize, 1f*ScaleSize, 1f*ScaleSize, 20, 20,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        Model[] models=new Model[]{redBall,greenBall,yellowBall,pingBall,grayBall,blackBall,whiteBall};



       ScaleSize*=2f;

        for(int i=0;i<length;i++)
        {

            for (int j = 0; j < length; j++) {
                ModelInstance instance=null;



                if(board.cells[i][j]>-1)
                {
                    //  instance=new ModelInstance(greenBall);
                    instance=new ModelInstance(models[board.cells[i][j]]);
                }
                if(instance!=null) {
                    instance.transform.translate(-8, -8, 0).translate(i - (j - 8) * 0.5f, j, 0);//
                    //     instance.transform.translate(-length / 2 * ScaleSize, -length / 2 * ScaleSize, 0).translate((i - j * 0.5f) * ScaleSize, (j) * ScaleSize, 0) ;
                    instances.add(instance);
                }
            }
        }

        Model  chequerBoard= modelBuilder.createCylinder(length*ScaleSize, 1 ,  length*ScaleSize, 50,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance instance =new ModelInstance(chequerBoard);
        instance.transform.translate(0,0,-1*ScaleSize).rotate(1,0,0,90);
        instances.add(instance);



    }


    private CameraInputController createController()
    {


        CameraInputController    camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
        return camController;
    }
}
